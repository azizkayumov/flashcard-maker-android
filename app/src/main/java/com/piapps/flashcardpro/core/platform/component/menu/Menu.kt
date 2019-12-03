package com.piapps.flashcardpro.core.platform.component.menu

class Menu(var menuTextColor: Int = android.R.attr.textColorPrimary, var backgroundColor: Int = android.R.color.white) {

    private val menus = arrayListOf<MenuItem>()

    fun addMenu(id: Int, s: String) {
        menus.add(MenuItem(id, s))
    }

    fun removeMenu(id: Int) {
        menus.removeAll { it.id == id }
    }

    fun getMenu(position: Int): MenuItem? {
        if (position >= 0 && position < menus.size)
            return menus[position]
        return null
    }

    fun getMenuById(id: Int): MenuItem? {
        return menus.find { it.id == id }
    }

    fun clear() {
        menus.clear()
    }

    fun size() = menus.size

}