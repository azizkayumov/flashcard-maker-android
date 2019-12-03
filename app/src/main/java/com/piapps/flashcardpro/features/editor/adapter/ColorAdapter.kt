package com.piapps.flashcardpro.features.editor.adapter

import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.piapps.flashcardpro.features.editor.adapter.cells.ColorItemUI
import de.hdodenhof.circleimageview.CircleImageView
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.imageResource

/**
 * Created by abduaziz on 2019-10-06 at 20:08.
 */

class ColorAdapter : RecyclerView.Adapter<ColorAdapter.ViewHolder>() {

    val list = arrayListOf<ColorEntity>()

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(ColorItemUI().createView(AnkoContext.Companion.create(p0.context, p0)))
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.cIv.imageResource = list[p1].color
        p0.ivSelected.visibility = if (list[p1].isSelected) View.VISIBLE else View.GONE
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val cIv: CircleImageView
        val ivSelected: AppCompatImageView

        init {
            cIv = itemView.findViewById(ColorItemUI.ivId)
            ivSelected = itemView.findViewById(ColorItemUI.ivSelectedId)
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            onItemClickListener?.onItemClick(list[adapterPosition])
            list.forEach {
                it.isSelected = false
            }
            list[adapterPosition].isSelected = true
            notifyDataSetChanged()
        }
    }

    class ColorEntity(val color: Int, var isSelected: Boolean = false)

    var onItemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(c: ColorEntity)
    }
}