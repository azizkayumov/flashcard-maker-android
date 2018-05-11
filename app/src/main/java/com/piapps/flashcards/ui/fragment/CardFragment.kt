package com.piapps.flashcards.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.piapps.flashcards.R
import com.piapps.flashcards.application.Flashcards
import com.piapps.flashcards.model.Card
import com.piapps.flashcards.ui.SetActivity
import com.piapps.flashcards.ui.anim.FlipAnimation
import com.piapps.flashcards.util.toColor
import kotlinx.android.synthetic.main.fragment_card.*

/**
 * Created by abduaziz on 5/6/18.
 */
class CardFragment : Fragment() {

    companion object {
        fun newInstance(id: Long): CardFragment {
            val fragment = CardFragment()
            val bundle = Bundle()
            bundle.putLong("id", id)
            fragment.arguments = bundle
            return fragment
        }
    }

    var id: Long = 1L
    lateinit var flip: FlipAnimation
    lateinit var flipBack: FlipAnimation

    lateinit var card: Card

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        id = arguments.getLong("id", 1L)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_card, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        card = Flashcards.instance.cards().get(id)

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

    fun flip() {
        if (relativeLayoutBack.visibility == View.GONE) {
            cardViewRoot.startAnimation(flip)
        } else {
            cardViewRoot.startAnimation(flipBack)
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