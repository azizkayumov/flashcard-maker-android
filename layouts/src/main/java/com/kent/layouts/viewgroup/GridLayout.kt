package com.kent.layouts.viewgroup

import android.app.Activity
import android.content.Context
import android.view.ViewGroup
import android.widget.GridLayout

/**
 * Created by abduaziz on 2020-02-23 at 06:39.
 */

inline fun Context.gridLayout(init: GridLayout.() -> Unit): GridLayout {
    return GridLayout(this).apply(init)
}

inline fun ViewGroup.gridLayout(init: GridLayout.() -> Unit): GridLayout {
    val f = GridLayout(context).apply(init)
    addView(f)
    return f
}

inline fun Activity.gridLayout(init: GridLayout.() -> Unit): GridLayout {
    val f = GridLayout(this).apply(init)
    setContentView(f)
    return f
}