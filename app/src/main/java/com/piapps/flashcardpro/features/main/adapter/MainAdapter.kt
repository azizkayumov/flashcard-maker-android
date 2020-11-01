package com.piapps.flashcardpro.features.main.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.kent.layouts.textColorResource
import com.piapps.flashcardpro.R
import com.piapps.flashcardpro.core.db.tables.SetDb
import com.piapps.flashcardpro.core.extension.color
import com.piapps.flashcardpro.core.extension.getLocalizedString
import com.piapps.flashcardpro.core.extension.toColor
import com.piapps.flashcardpro.core.platform.MultiSortedList
import com.piapps.flashcardpro.features.main.adapter.cells.SetItemUI
import com.piapps.flashcardpro.features.main.entity.SetView
import kotlin.math.abs

/**
 * Created by abduaziz on 2019-09-25 at 23:20.
 */

class MainAdapter : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    private val list = MultiSortedList(object : MultiSortedList.UpdateCallback<SetView> {
        override fun sortBy(i1: SetView, i2: SetView): Int {
            return i2.id.compareTo(i1.id)
        }

        override fun updateBy(oldItem: SetView, newItem: SetView): Int {
            return newItem.id.compareTo(oldItem.id)
        }
    }, this)

    fun clearSets() {
        list.clear()
    }

    fun addAll(sets: List<SetView>) {
        sets.forEach {
            list.add(it)
        }
    }

    fun updateSet(updatedSet: SetView) {
        list.update(updatedSet)
    }

    fun removeSet(set: SetDb) {
        list.remove(set.toSetView())
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(SetItemUI().createView(p0.context))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val parent: LinearLayout
        val tvTitle: TextView
        val tvCount: TextView

        init {
            parent = itemView.findViewById(SetItemUI.parentId)
            tvTitle = itemView.findViewById(SetItemUI.tvTitleId)
            tvCount = itemView.findViewById(SetItemUI.tvCountId)
            parent.setOnClickListener(this)
        }

        fun bind(item: SetView) {
            tvTitle.text =
                if (item.title.isNotBlank()) item.title else itemView.context.getLocalizedString(R.string.untitled_set)
            tvCount.text =
                itemView.context.getLocalizedString(
                    R.string.n_cards,
                    arrayOf(item.flashcardsCount.toString())
                )

            if (item.color.isNotBlank())
                parent.setBackgroundColor(item.color.toColor())
            else
                parent.setBackgroundColor(ContextCompat.getColor(itemView.context, abs(item.id).color()))

            if (item.colorText.isNotBlank()) {
                tvTitle.setTextColor(item.colorText.toColor())
                tvCount.setTextColor(item.colorText.toColor())
            } else {
                tvTitle.textColorResource = R.color.colorPrimaryText
                tvCount.textColorResource = R.color.colorPrimaryText
            }
        }

        override fun onClick(v: View?) {
            onSetClickedListener?.onSetClicked(list[adapterPosition])
        }
    }

    var onSetClickedListener: OnSetClickedListener? = null

    interface OnSetClickedListener {
        fun onSetClicked(set: SetView)
    }
}