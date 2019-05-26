package com.piapps.flashcardpro.ui.fragment

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
import com.piapps.flashcardpro.R
import com.piapps.flashcardpro.application.Flashcards
import com.piapps.flashcardpro.model.Card
import com.piapps.flashcardpro.ui.SetActivity
import com.piapps.flashcardpro.ui.anim.FlipAnimation
import com.piapps.flashcardpro.util.toColor
import kotlinx.android.synthetic.main.fragment_card.*

/**
 * Created by abduaziz on 5/6/18.
 */
class CardFragment : Fragment() {

    companion object {
        fun newInstance(id: Long, isDeletable: Boolean = true, isFlippable: Boolean = true): CardFragment {
            val fragment = CardFragment()
            val bundle = Bundle()
            bundle.putLong("id", id)
            bundle.putBoolean("isDeletable", isDeletable)
            bundle.putBoolean("isFlippable", isFlippable)
            fragment.arguments = bundle
            return fragment
        }
    }

    var id: Long = 1L
    var isDeletable: Boolean = true
    var isFlippable: Boolean = true
    lateinit var flip: FlipAnimation
    lateinit var flipBack: FlipAnimation
    var card: Card = Card().apply {
        this.front = ""
        this.back = ""
    }
    var isEditingBack = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        id = arguments!!.getLong("id", 1L)
        isDeletable = arguments!!.getBoolean("isDeletable", true)
        isFlippable = arguments!!.getBoolean("isFlippable", true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_card, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        card = Flashcards.instance.cards().get(id) ?: Card(System.currentTimeMillis(), 1L)

        if (!isDeletable) {
            imageViewDelete.visibility = View.GONE
            imageViewEdit.visibility = View.GONE
        }

        if (!isFlippable) {
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

        imageViewEdit.setOnClickListener {
            (activity as SetActivity).showBottomSheet()
        }

        // set images if they are present
        if (card.frontImage.isNotBlank()) {
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

        if (card.backImage.isNotBlank()) {
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
        } else {
            Glide.with(this).load(path).into(imageViewFront)
            card.frontImage = path
        }
        Flashcards.instance.cards().put(card)
    }

    fun setText(text: String, isBack: Boolean = false) {
        if (isBack) {
            card.back = text
            textBack.text = text
        } else {
            card.front = text
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
        if (relativeLayoutBack?.visibility == View.GONE) {
            return 0
        } else {
            return 1
        }
    }
}