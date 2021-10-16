package com.piapps.flashcardpro.features.editor.adapter

import android.graphics.Color
import android.util.TypedValue
import android.view.ScaleGestureDetector
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.kent.layouts.setIconColor
import com.piapps.flashcardpro.R
import com.piapps.flashcardpro.core.db.tables.CardDb
import com.piapps.flashcardpro.core.extension.*
import com.piapps.flashcardpro.core.platform.LONG_ANIMATION
import com.piapps.flashcardpro.core.platform.component.FlipAnimation
import com.piapps.flashcardpro.features.editor.adapter.cells.CardUI
import java.util.*

/**
 * Created by abduaziz on 2019-10-01 at 21:26.
 */

class CardsEditorAdapter : RecyclerView.Adapter<CardsEditorAdapter.ViewHolder>() {

    val list = arrayListOf<CardDb>()
    var backgroundColor = ""
    var textColor = ""
    var isSelecting = false

    fun updateBackgroundColor(color: String) {
        backgroundColor = color
        list.forEach {
            it.frontColor = backgroundColor
            it.backColor = backgroundColor
        }
        notifyDataSetChanged()
    }

    fun updateTextColor(color: String) {
        textColor = color
        list.forEach {
            it.frontTextColor = textColor
            it.backTextColor = textColor
        }
        notifyDataSetChanged()
    }

    fun addNewCard(card: CardDb, pos: Int) {
        list.add(pos, card)
        notifyItemInserted(pos)
    }

    fun addAll(cards: List<CardDb>, index: Int = -1) {
        // remove if the pasting cards exist
        for (card in cards) {
            for (i in 0 until list.size) {
                if (list[i].id == card.id) {
                    list.removeAt(i)
                    break
                }
            }
        }

        // paste
        if (index < 0) {
            list.addAll(0, cards)
        } else if (index >= list.size) {
            list.addAll(cards)
        } else {
            list.addAll(index, cards)
        }
        notifyDataSetChanged()
    }

    fun updateCard(card: CardDb) {
        for (i in 0 until list.size) {
            if (list[i].id == card.id) {
                list[i] = card
                notifyItemChanged(i)
                break
            }
        }
    }

    fun remove(card: CardDb) {
        val index = list.indexOfFirst { c -> c.id == card.id }
        if (index != -1) {
            list.removeAt(index)
            notifyItemRemoved(index)
            notifyItemRangeChanged(index, list.size)
        }
    }

    fun reverseCards() {
        list.forEach {
            it.reverse()
        }
        notifyDataSetChanged()
    }

    fun shuffleCards() {
        list.shuffle()
        updateOrders()
    }

    fun sortCardsAlphabetically() {
        list.sortBy {
            it.front.toLowerCase(Locale.getDefault())
        }
        updateOrders()
    }

    fun updateOrders() {
        list.forEachIndexed { index, card ->
            card.order = index
        }
        notifyDataSetChanged()
    }

    fun unselectCards() {
        for (i in 0 until list.size) {
            if (list[i].isSelected) {
                list[i].isSelected = false
                notifyItemChanged(i)
            }
        }
    }

    fun hideSelectedCards() {
        list.removeAll { it.isSelected }
        notifyDataSetChanged()
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(CardUI().createView(p0.context))
    }

    override fun onBindViewHolder(holder: ViewHolder, pos: Int) {
        holder.bind(list[pos])
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener, ScaleGestureDetector.OnScaleGestureListener {

        val root: CardView

        val front: FrameLayout
        val tvFront: TextView
        val ivFront: AppCompatImageView
        val ivFrontFlip: AppCompatImageView

        val back: FrameLayout
        val tvBack: TextView
        val ivBack: AppCompatImageView
        val ivBackFlip: AppCompatImageView

        val ivDelete: AppCompatImageView
        val ivEdit: AppCompatImageView
        val ivSelected: AppCompatImageView


        val scaleGestureDetector: ScaleGestureDetector

        init {
            root = itemView.findViewById(CardUI.rootId)

            front = itemView.findViewById(CardUI.frontId)
            tvFront = itemView.findViewById(CardUI.frontTvId)
            ivFront = itemView.findViewById(CardUI.frontIvId)
            ivFrontFlip = itemView.findViewById(CardUI.frontIvFlipId)

            back = itemView.findViewById(CardUI.backId)
            tvBack = itemView.findViewById(CardUI.backTvId)
            ivBack = itemView.findViewById(CardUI.backIvId)
            ivBackFlip = itemView.findViewById(CardUI.backIvFlipId)

            ivEdit = itemView.findViewById(CardUI.ivEditId)
            ivDelete = itemView.findViewById(CardUI.ivDeleteId)
            ivSelected = itemView.findViewById(CardUI.ivSelectedIndicatorId)

            tvFront.setOnClickListener(this)
            tvBack.setOnClickListener(this)

            ivDelete.setOnClickListener(this)
            ivSelected.setOnClickListener(this)
            ivEdit.setOnClickListener(this)
            ivFrontFlip.setOnClickListener(this)
            ivBackFlip.setOnClickListener(this)

            scaleGestureDetector = ScaleGestureDetector(itemView.context, this)
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

            tvFront.setOnTouchListener { _, event ->
                scaleGestureDetector.onTouchEvent(event)
                false
            }
            tvBack.setOnTouchListener { _, event ->
                scaleGestureDetector.onTouchEvent(event)
                false
            }

            // set background colors
            val backColor = backgroundColor.toColor()
            front.setBackgroundColor(backColor)
            back.setBackgroundColor(backColor)

            // set text colors
            val txtColor = textColor.toColor()
            tvFront.setTextColor(txtColor)
            tvBack.setTextColor(txtColor)
            ivFrontFlip.setIconColor(txtColor)
            ivBackFlip.setIconColor(txtColor)
            ivFrontFlip.setIconColor(txtColor)
            ivSelected.setIconColor(txtColor)
            ivEdit.setIconColor(txtColor)
            ivDelete.setIconColor(txtColor)

            // set images
            if (card.frontImage.isNotBlank())
                ivFront.load(card.frontImage)
            else
                ivFront.load("")

            if (card.backImage.isNotBlank())
                ivBack.load(card.backImage)
            else
                ivBack.load("")

            // set font size
            tvFront.setTextSize(TypedValue.COMPLEX_UNIT_SP, card.frontTextSize ?: 28F)
            tvBack.setTextSize(TypedValue.COMPLEX_UNIT_SP, card.backTextSize ?: 28F)

            showIsSelected()
            if (isSelecting)
                hideActions()
            else
                showActions()
        }

        private fun flip(reverse: Boolean = false) {
            val anim = FlipAnimation(front, back, LONG_ANIMATION.toInt())
            list[adapterPosition].reverse()
            if (reverse) anim.reverse()
            root.startAnimation(anim)
        }

        override fun onClick(v: View?) {
            if (isSelecting && !(v == ivFrontFlip || v == ivBackFlip)) {
                toggleSelection()
                return
            }
            when (v) {
                ivEdit -> {
                    onCardClickListener?.onCardEditClick()
                }
                tvFront -> {
                    flip()
//                    onCardClickListener?.onCardEditTextClick()
                }
                tvBack -> {
                    flip(true)
//                    onCardClickListener?.onCardEditTextClick()
                }
                ivDelete -> {
                    onCardClickListener?.onCardDeleteClick()
                }
                ivFrontFlip -> {
                    flip()
                }
                ivBackFlip -> {
                    flip(true)
                }
                ivSelected -> {
                    toggleSelection()
                }
            }
        }

        private fun toggleSelection() {
            list[adapterPosition].isSelected = !list[adapterPosition].isSelected
            showIsSelected()
            onCardClickListener?.onCardSelectionToggle()
        }

        private fun showIsSelected() {
            if (list[adapterPosition].isSelected) {
                ivSelected.setBackgroundResource(
                    if (itemView.context.appTheme().isNight())
                        R.drawable.circle_gray
                    else
                        R.drawable.circle_white
                )
                ivSelected.setImageResource(R.drawable.ic_check_circle)
                ivSelected.setIconColor(ContextCompat.getColor(itemView.context, R.color.c7))
                hideActions()
            } else {
                ivSelected.setBackgroundColor(Color.TRANSPARENT)
                ivSelected.setImageResource(R.drawable.ic_check)
                ivSelected.setIconColor(textColor.toColor())
                showActions()
            }
        }

        private fun hideActions() {
            ivEdit.visibility = View.INVISIBLE
            ivDelete.visibility = View.INVISIBLE
            // ivFrontFlip.visibility = View.INVISIBLE
            // ivBackFlip.visibility = View.INVISIBLE
        }

        private fun showActions() {
            ivEdit.visibility = View.VISIBLE
            ivDelete.visibility = View.VISIBLE
            // ivFrontFlip.visibility = View.VISIBLE
            // ivBackFlip.visibility = View.VISIBLE
        }

        private fun isEditingBack(): Boolean {
            return front.visibility == View.GONE
        }

        override fun onScaleBegin(detector: ScaleGestureDetector?): Boolean {
            return true
        }

        private var newTextSize = 28F
        override fun onScale(detector: ScaleGestureDetector?): Boolean {
            val textView = if (isEditingBack()) tvBack else tvFront

            val oldTextSize = textView.textSize / itemView.context.resources.displayMetrics.scaledDensity
            val scaleFactor = scaleGestureDetector.scaleFactor
            newTextSize = oldTextSize * scaleFactor
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, newTextSize)
            return true
        }

        override fun onScaleEnd(detector: ScaleGestureDetector?) {
            onCardZoomGestureListener?.onCardTextSizeChangedByPinchingKungfuPanda(newTextSize)
        }
    }

    var onCardClickListener: OnCardClickListener? = null

    interface OnCardClickListener {
        fun onCardEditClick()
        fun onCardDeleteClick()
        fun onCardEditTextClick()
        fun onCardSelectionToggle()
    }

    var onCardZoomGestureListener: OnCardZoomGestureListener? = null
    interface OnCardZoomGestureListener{
        fun onCardTextSizeChangedByPinchingKungfuPanda(newTextSize: Float)
    }
}