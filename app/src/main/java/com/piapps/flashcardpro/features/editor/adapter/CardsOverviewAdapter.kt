package com.piapps.flashcardpro.features.editor.adapter

import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.piapps.flashcardpro.R
import com.piapps.flashcardpro.core.db.tables.CardDb
import com.piapps.flashcardpro.core.extension.getLocalizedString
import com.piapps.flashcardpro.core.extension.toColor
import com.piapps.flashcardpro.features.editor.adapter.cells.CardOverviewUI
import org.jetbrains.anko.AnkoContext

/**
 * Created by abduaziz on 2019-09-30 at 23:38.
 */

class CardsOverviewAdapter(var list: ArrayList<CardDb> = arrayListOf<CardDb>()) :
    RecyclerView.Adapter<CardsOverviewAdapter.ViewHolder>() {

    fun addAll(cards: List<CardDb>) {
        list.addAll(cards)
        notifyDataSetChanged()
    }

    fun remove(card: CardDb) {
        list.remove(card)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(CardOverviewUI().createView(AnkoContext.Companion.create(p0.context, p0)))
    }

    override fun onBindViewHolder(holder: ViewHolder, pos: Int) {
        holder.bind(list[pos])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvFront: TextView
        var ivFront: AppCompatImageView
        var parent: View

        init {
            tvFront = itemView.findViewById(CardOverviewUI.tvFrontId)
            ivFront = itemView.findViewById(CardOverviewUI.ivFrontId)
            parent = itemView.findViewById(CardOverviewUI.parentId)
        }

        fun bind(card: CardDb) {
            // set texts
            tvFront.text =
                if (card.front.isNotBlank()) card.front else itemView.context.getLocalizedString(R.string.front_side)

            // set background colors
            if (!card.frontColor.isBlank())
                parent.setBackgroundColor(card.frontColor.toColor())

            // set text colors
            if (!card.frontTextColor.isBlank())
                tvFront.setTextColor(card.frontTextColor.toColor())
        }
    }
}