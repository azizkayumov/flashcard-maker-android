package com.piapps.flashcardpro.core.platform.component.bottom

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.piapps.flashcardpro.R

class BottomMenuAdapter(var bottomMenu: BottomMenu, var bottomMenuClickListener: OnBottomMenuClickListener?) :
    RecyclerView.Adapter<BottomMenuAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(BottomMenuItemUI().createView(parent.context))

    override fun getItemCount() = bottomMenu.size()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        bottomMenu.getMenu(position)?.let {
            holder.bind(it.text, it.iconRes)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var ivIcon: AppCompatImageView
        var tvTitle: TextView

        init {
            itemView.setOnClickListener(this)
            ivIcon = itemView.findViewById(BottomMenuItemUI.ivId)
            tvTitle = itemView.findViewById(BottomMenuItemUI.tvId)
        }

        fun bind(title: String, iconResource: Int) {
            tvTitle.text = title
            if (iconResource == R.color.transparent)
                ivIcon.visibility = View.GONE
            else
                ivIcon.visibility = View.VISIBLE
            ivIcon.setImageResource(iconResource)
        }

        override fun onClick(v: View?) {
            bottomMenu.getMenu(adapterPosition)?.let {
                bottomMenuClickListener?.bottomMenuClick(it)
            }
        }
    }
}