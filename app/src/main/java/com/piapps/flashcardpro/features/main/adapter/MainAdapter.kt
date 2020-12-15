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
import com.piapps.flashcardpro.features.main.adapter.cells.SetItemUI
import kotlin.math.abs

/**
 * Created by abduaziz on 2019-09-25 at 23:20.
 */

class MainAdapter : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    val list = arrayListOf<SetDb>()

    fun clearSets() {
        list.clear()
        notifyDataSetChanged()
    }

    fun addAll(sets: List<SetDb>) {
        list.addAll(sets)
    }

    fun updateSet(updatedSet: SetDb) {
        for (i in 0 until list.size){
            if (list[i].id == updatedSet.id){
                list[i] = updatedSet
                notifyItemChanged(i)
            }
        }
    }

    fun removeSet(set: SetDb) {
        for (i in 0 until list.size){
            if (list[i].id == set.id){
                list.removeAt(i)
                notifyItemRemoved(i)
            }
        }
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(SetItemUI().createView(p0.context))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
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

        fun bind(item: SetDb) {
            tvTitle.text =
                if (item.title.isNotBlank()) item.title else itemView.context.getLocalizedString(R.string.untitled_set)
            tvCount.text =
                itemView.context.getLocalizedString(
                    R.string.n_cards,
                    arrayOf(item.count.toString())
                )

            if (item.color.isNotBlank())
                parent.setBackgroundColor(item.color.toColor())
            else
                parent.setBackgroundColor(
                    ContextCompat.getColor(
                        itemView.context,
                        abs(item.id).color()
                    )
                )

            if (!item.textColor.isNullOrBlank()) {
                tvTitle.setTextColor(item.textColor!!.toColor())
                tvCount.setTextColor(item.textColor!!.toColor())
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
        fun onSetClicked(set: SetDb)
    }
}