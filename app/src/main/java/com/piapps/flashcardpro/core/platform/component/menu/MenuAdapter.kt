package com.piapps.flashcardpro.core.platform.component.menu

import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import com.piapps.flashcardpro.R
import com.piapps.flashcardpro.core.extension.appTheme
import com.piapps.flashcardpro.core.extension.ellipsize
import com.piapps.flashcardpro.core.extension.setRippleEffect
import com.piapps.flashcardpro.core.extension.setStyle
import org.jetbrains.anko.dip
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.textColorResource
import org.jetbrains.anko.wrapContent

internal class MenuAdapter(var menu: Menu) : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    val TV_MENU_ID = 1995

    var onMenuClickListener: OnMenuClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val frame = FrameLayout(parent.context)
        frame.layoutParams = ViewGroup.LayoutParams(matchParent, wrapContent)

        val tv = TextView(parent.context)
        tv.id = TV_MENU_ID
        tv.layoutParams = FrameLayout.LayoutParams(matchParent, tv.dip(48))
        tv.setPadding(tv.dip(16), 0, tv.dip(16), 0)
        tv.gravity = Gravity.CENTER_VERTICAL
        tv.setStyle(parent.context, android.R.style.TextAppearance_DeviceDefault_Widget_TextView_PopupMenu)
        tv.textColorResource = parent.context.appTheme().colorPrimaryText
        tv.ellipsize()

        frame.addView(tv)
        return MenuViewHolder(frame)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val menuItem = menu.getMenu(position)
        menuItem?.let {
            holder.tv.text = it.text
        }
    }

    override fun getItemCount(): Int {
        return menu.size()
    }

    inner class MenuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tv: TextView = itemView.findViewById(TV_MENU_ID)

        init {
            itemView.setOnClickListener {
                menu.getMenu(adapterPosition)?.let {
                    onMenuClickListener?.onMenuClick(it)
                }
            }
            itemView.setRippleEffect()
        }

    }
}