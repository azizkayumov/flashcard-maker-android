package com.piapps.flashcardpro.features.main.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.piapps.flashcardpro.features.main.adapter.cells.*
import com.piapps.flashcardpro.features.main.entity.NavView

/**
 * Created by abduaziz on 2019-09-26 at 12:29.
 */

class NavigationAdapter(val list: ArrayList<NavView> = arrayListOf<NavView>()) :
    RecyclerView.Adapter<NavigationAdapter.ViewHolder>() {

    fun add(item: NavView) {
        list.add(item)
        notifyDataSetChanged()
    }

    fun showLabels(items: List<NavView>) {
        list.removeAll { it.isLabel() }
        for (item in items) {
            list.add(item)
        }
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return list[position].type
    }

    override fun onCreateViewHolder(p0: ViewGroup, viewType: Int): ViewHolder {
        if (viewType == NavView.TYPE_HEADER) // todo
            return ViewHolder(NavHeaderUI().createView(p0.context))
        if (viewType == NavView.TYPE_MENU_GROUP)
            return ViewHolder(NavGroupUI().createView(p0.context))
        if (viewType == NavView.TYPE_MENU)
            return ViewHolder(NavMenuUI().createView(p0.context))
        if (viewType == NavView.TYPE_LABEL)
            return ViewHolder(NavMenuUI().createView(p0.context))
        if (viewType == NavView.TYPE_SMALL_DIVIDER)
            return ViewHolder(NavSmallDividerUI().createView(p0.context))
        return ViewHolder(NavDividerUI().createView(p0.context))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (list[position].type) {
            NavView.TYPE_HEADER -> {

            }
            NavView.TYPE_MENU_GROUP -> {
                holder.bindMenuGroup(list[position])
            }
            NavView.TYPE_MENU, NavView.TYPE_LABEL -> {
                holder.bindMenu(list[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        var divider: View? = null
        var iv: AppCompatImageView? = null
        var tv: TextView? = null

        init {
            divider = itemView.findViewById(NavMenuUI.dividerId)
            iv = itemView.findViewById(NavMenuUI.ivId)
            tv = itemView.findViewById(NavMenuUI.tvId)
            if (iv != null && tv != null)
                itemView.setOnClickListener(this)
        }

        fun bindHeader(item: NavView) {

        }

        fun bindMenuGroup(item: NavView) {
            tv?.text = item.title
        }

        fun bindMenu(item: NavView) {
            iv?.setImageResource(item.icon)
            tv?.text = item.title
        }

        override fun onClick(v: View?) {
            onNavigationClickListener?.onNavigationClick(list[adapterPosition])
        }
    }

    var onNavigationClickListener: OnNavigationClickListener? = null

    interface OnNavigationClickListener {
        fun onNavigationClick(nav: NavView)
    }
}