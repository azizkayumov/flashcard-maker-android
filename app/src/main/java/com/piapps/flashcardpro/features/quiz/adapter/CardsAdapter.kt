package com.piapps.flashcardpro.features.quiz.adapter

import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.kent.layouts.textColorResource
import com.piapps.flashcardpro.R
import com.piapps.flashcardpro.core.db.tables.CardDb
import com.piapps.flashcardpro.core.extension.color
import com.piapps.flashcardpro.core.extension.getLocalizedString
import com.piapps.flashcardpro.core.extension.load
import com.piapps.flashcardpro.core.extension.toColor
import com.piapps.flashcardpro.core.platform.LONG_ANIMATION
import com.piapps.flashcardpro.core.platform.component.FlipAnimation

/**
 * Created by abduaziz on 2019-10-30 at 22:56.
 */

class CardsAdapter : RecyclerView.Adapter<CardsAdapter.ViewHolder>() {

    val list = arrayListOf<CardDb>()
    var defaultColor = ""

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(CardUI().createView(p0.context))
    }

    override fun onBindViewHolder(holder: CardsAdapter.ViewHolder, pos: Int) {
        holder.bind(list[pos])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val root: CardView

        val front: FrameLayout
        val tvFront: TextView
        val ivFront: AppCompatImageView

        val back: FrameLayout
        val tvBack: TextView
        val ivBack: AppCompatImageView

        init {
            root = itemView.findViewById(CardUI.rootId)

            front = itemView.findViewById(CardUI.frontId)
            tvFront = itemView.findViewById(CardUI.frontTvId)
            ivFront = itemView.findViewById(CardUI.frontIvId)

            back = itemView.findViewById(CardUI.backId)
            tvBack = itemView.findViewById(CardUI.backTvId)
            ivBack = itemView.findViewById(CardUI.backIvId)
        }

        fun bind(card: CardDb) {
            front.visibility = View.VISIBLE
            back.visibility = View.GONE

            card.order = adapterPosition
            // set texts
            tvFront.text =
                if (card.front.isNotBlank() || card.frontImage.isNotBlank()) card.front else
                    itemView.context.getLocalizedString(R.string.text)
            tvBack.text =
                if (card.back.isNotBlank() || card.backImage.isNotBlank()) card.back else
                    itemView.context.getLocalizedString(R.string.text)

            // set background colors
            if (card.frontColor.isNotBlank())
                front.setBackgroundColor(card.frontColor.toColor())
            else if (defaultColor.isNotBlank())
                front.setBackgroundColor(defaultColor.toColor())
            else
                front.setBackgroundColor(ContextCompat.getColor(itemView.context, card.setId.color()))

            if (card.backColor.isNotBlank())
                back.setBackgroundColor(card.backColor.toColor())
            else if (defaultColor.isNotBlank())
                back.setBackgroundColor(defaultColor.toColor())
            else
                back.setBackgroundColor(ContextCompat.getColor(itemView.context, card.setId.color()))

            // set text colors
            if (card.frontTextColor.isNotBlank())
                tvFront.setTextColor(card.frontTextColor.toColor())
            else
                tvFront.textColorResource = R.color.colorPrimaryText

            if (card.backTextColor.isNotBlank())
                tvBack.setTextColor(card.backTextColor.toColor())
            else
                tvBack.textColorResource = R.color.colorPrimaryText

            // set images
            if (card.frontImage.isNotBlank())
                ivFront.load(card.frontImage)
            if (card.backImage.isNotBlank())
                ivBack.load(card.backImage)

            // set font size
            tvFront.setTextSize(TypedValue.COMPLEX_UNIT_SP, card.frontTextSize ?: 28F)
            tvBack.setTextSize(TypedValue.COMPLEX_UNIT_SP, card.backTextSize ?: 28F)
        }

        fun flip() {
            val anim = FlipAnimation(front, back, LONG_ANIMATION.toInt())
            if (back.visibility == View.VISIBLE) anim.reverse()
            root.startAnimation(anim)
        }
    }


}