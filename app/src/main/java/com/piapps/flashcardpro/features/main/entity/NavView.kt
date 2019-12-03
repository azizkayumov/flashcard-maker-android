package com.piapps.flashcardpro.features.main.entity

import com.piapps.flashcardpro.R

/**
 * Created by abduaziz on 2019-09-26 at 12:31.
 */

class NavView(
    var id: Long = 0,
    var type: Int = TYPE_DIVIDER,
    var title: String = "",
    var icon: Int = R.drawable.ic_menu
) {
    companion object {
        val TYPE_HEADER = 10
        val TYPE_DIVIDER = 11
        val TYPE_SMALL_DIVIDER = 12
        val TYPE_MENU_GROUP = 13
        val TYPE_MENU = 14
        val TYPE_LABEL = 15

        fun header(id: Long) = NavView(id = id, type = TYPE_HEADER)

        fun menu(id: Long, t: String, i: Int) = NavView(id = id, type = NavView.TYPE_MENU, title = t, icon = i)

        fun divider(id: Long) = NavView(id = id)
        fun smallDivider(id: Long) = NavView(id = id, type = TYPE_SMALL_DIVIDER)

        fun menuGroup(id: Long, t: String) = NavView(id = id, type = TYPE_MENU_GROUP, title = t)

        fun label(id: Long, t: String) = NavView(id = id, type = TYPE_LABEL, title = t, icon = R.drawable.ic_label)
    }

    fun isLabel() = type == TYPE_LABEL
    override fun toString(): String {
        return "NavView(id=$id, title='$title')"
    }
}