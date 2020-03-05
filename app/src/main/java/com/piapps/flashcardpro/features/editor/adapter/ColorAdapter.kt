package com.piapps.flashcardpro.features.editor.adapter

import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.kent.layouts.setIconColor
import com.piapps.flashcardpro.features.editor.adapter.cells.ColorItemUI
import de.hdodenhof.circleimageview.CircleImageView

/**
 * Created by abduaziz on 2019-10-06 at 20:08.
 */

class ColorAdapter : RecyclerView.Adapter<ColorAdapter.ViewHolder>() {

    val list = arrayListOf<ColorEntity>()

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(ColorItemUI().createView(p0.context))
    }

    override fun onBindViewHolder(p0: ViewHolder, pos: Int) {
        p0.cIv.setImageResource(list[pos].color)
        p0.ivSelected.visibility = if (list[pos].isSelected) View.VISIBLE else View.GONE
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