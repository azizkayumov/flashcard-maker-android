package com.piapps.flashcardpro.core.platform.component.bottom

import com.piapps.flashcardpro.R

class BottomMenu(
    var menuTextColor: Int = android.R.attr.textColorPrimary,
    var backgroundColor: Int = android.R.color.white
) {

    private val menus = arrayListOf<BottomMenuItem>()

    fun addMenu(id: Long, s: String, iconRes: Int = R.color.transparent) {
        menus.add(BottomMenuItem(id, s, iconRes))
    }

    fun removeMenu(id: Long) {
        menus.removeAll { it.id == id }
    }

    fun getMenu(position: Int): BottomMenuItem? {
        if (position >= 0 && position < menus.size)
            return menus[position]
        return null
    }

    fun getMenuById(id: Long): BottomMenuItem? {
        return menus.find { it.id == id }
    }

    fun clear() {
        menus.clear()
    }

    fun size() = menus.size

}