package com.kent.layouts.viewgroup

import android.app.Activity
import android.content.Context
import android.view.ViewGroup
import androidx.drawerlayout.widget.DrawerLayout

/**
 * Created by abduaziz on 2020-02-24 at 07:01.
 */

inline fun Context.drawerLayout(init: DrawerLayout.() -> Unit): DrawerLayout {
    return DrawerLayout(this).apply(init)
}

inline fun ViewGroup.drawerLayout(init: DrawerLayout.() -> Unit): DrawerLayout {
    val f = DrawerLayout(context).apply(init)
    addView(f)
    return f
}

inline fun Activity.drawerLayout(init: DrawerLayout.() -> Unit): DrawerLayout {
    val f = DrawerLayout(this).apply(init)
    setContentView(f)
    return f
}