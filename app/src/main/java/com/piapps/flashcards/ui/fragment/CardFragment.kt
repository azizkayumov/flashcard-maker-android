package com.piapps.flashcards.ui.fragment

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.piapps.flashcards.R
import com.piapps.flashcards.application.Flashcards
import com.piapps.flashcards.model.Card
import com.piapps.flashcards.ui.SetActivity
import com.piapps.flashcards.ui.anim.FlipAnimation
import com.piapps.flashcards.util.toColor
import kotlinx.android.synthetic.main.fragment_card.*
import org.jetbrains.anko.toast

/**
 * Created by abduaziz on 5/6/18.
 */
class CardFragment : Fragment() {

    companion object {
        fun newInstance(id: Long, isDeletable: Boolean = true): CardFragment {
            val fragment = CardFragment()
            val bundle = Bundle()
            bundle.putLong("id", id)
            bundle.putBoolean("isDeletable", isDeletable)
            fragment.arguments = bundle
            return fragment
        }
    }

    var id: Long = 1L
    var isDeletable: Boolean = true
    lateinit var flip: FlipAnimation
    lateinit var flipBack: FlipAnimation
    lateinit var card: Card
    var isEditingBack = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        id = arguments!!.getLong("id", 1L)
        isDeletable = arguments!!.getBoolean("isDeletable", true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_card, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        card = Flashcards.instance.cards().get(id)

        if (!isDeletable) {
            imageViewDelete.visibility = View.GONE
            imageViewFlip.visibility = View.GONE
        }

        // set texts
        textFront.text = card.front
        textBack.text = card.back

        // set background colors
        if (!card.frontColor.isBlank())
            relativeLayoutFront.setBackgroundColor(card.frontColor.toColor())
        if (!card.backColor.isBlank())
            relativeLayoutBack.setBackgroundColor(card.backColor.toColor())

        // set text colors
        if (!card.frontTextColor.isBlank())
            textFront.setTextColor(card.frontTextColor.toColor())
        if (!card.backTextColor.isBlank())
            textBack.setTextColor(card.backTextColor.toColor())

        // flip animations: they are separate anims not to be confused in future
        flip = FlipAnimation(relativeLayoutFront, relativeLayoutBack, 200)
        flipBack = FlipAnimation(relativeLayoutBack, relativeLayoutFront, 200, false)

        imageViewFlip.setOnClickListener { flip() }

        imageViewDelete.setOnClickListener {
            (activity as SetActivity).deleteCard()
        }

        // set images if they are present
        if (!card.frontImage.isBlank()) {
            Glide.with(this).load(card.frontImage).listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                    e?.printStackTrace()
                    return true
                }

                override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                    imageViewFront.setImageDrawable(resource)
                    return true
                }
            }).into(imageViewFront)
        }
        if (!card.backImage.isBlank()) {
            Glide.with(this).load(card.backImage).listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                    e?.printStackTrace()
                    return true
                }

                override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                    imageViewBack.setImageDrawable(resource)
                    return true
                }
            }).into(imageViewBack)
        }
    }

    fun setCardColor(hex: String, isBack: Boolean = false) {
        if (isBack) {
            card.backColor = hex
            relativeLayoutBack.setBackgroundColor(hex.toColor())
        } else {
            card.frontColor = hex
            relativeLayoutFront.setBackgroundColor(hex.toColor())
        }
        Flashcards.instance.cards().put(card)
    }

    fun setTextColor(hex: String, isBack: Boolean = false) {
        if (isBack) {
            card.backTextColor = hex
            textBack.setTextColor(hex.toColor())
        } else {
            card.frontTextColor = hex
            textFront.setTextColor(hex.toColor())
        }
        Flashcards.instance.cards().put(card)
    }

    fun setImage(path: String, isBack: Boolean = false) {
        if (isBack) {
            Glide.with(this).load(path).into(imageViewBack)
            card.backImage = path
            card.back = ""
            textBack.text = ""
        } else {
            Glide.with(this).load(path).into(imageViewFront)
            card.frontImage = path
            card.front = ""
            textFront.text = ""
        }
        Flashcards.instance.cards().put(card)
    }

    fun setText(text: String, isBack: Boolean = false) {
        if (isBack) {
            Glide.with(this).clear(imageViewBack)
            card.back = text
            card.backImage = ""
            textBack.text = text
        } else {
            Glide.with(this).clear(imageViewFront)
            card.front = text
            card.frontImage = ""
            textFront.text = text
        }
        Flashcards.instance.cards().put(card)
    }

    fun flip() {
        if (relativeLayoutBack.visibility == View.GONE) {
            cardViewRoot.startAnimation(flip)
            isEditingBack = true
        } else {
            cardViewRoot.startAnimation(flipBack)
            isEditingBack = false
        }
    }

    // used to identify which side we are editing, see SetActivity.kt
    fun side(): Int {
        if (relativeLayoutBack.visibility == View.GONE) {
            return 0
        } else {
            return 1
        }
    }
}