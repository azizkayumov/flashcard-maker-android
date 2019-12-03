package com.piapps.flashcardpro.core.platform.component.bottom

interface OnBottomMenuClickListener {
    fun bottomMenuClick(item: BottomMenuItem)
    fun bottomMenuClosed() {}
}