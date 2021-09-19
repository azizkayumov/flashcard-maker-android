package com.kent.layouts.viewgroup

import android.app.Activity
import android.content.Context
import android.view.ViewGroup
import android.widget.LinearLayout

/**
 * Created by abduaziz on 2020-02-23 at 06:35.
 */

inline fun Context.verticalLayout(init: LinearLayout.() -> Unit): LinearLayout {
    val l = LinearLayout(this).apply {
        orientation = LinearLayout.VERTICAL
        init()
    }
    return l
}

inline fun ViewGroup.verticalLayout(init: LinearLayout.() -> Unit): LinearLayout {
    val f = LinearLayout(context).apply {
        orientation = LinearLayout.VERTICAL
        init()
    }
    addView(f)
    return f
}

inline fun Activity.verticalLayout(init: LinearLayout.() -> Unit): LinearLayout {
    val l = LinearLayout(this).apply {
        orientation = LinearLayout.VERTICAL
        init()
    }
    setContentView(l)
    return l
}

inline fun Context.horizontalLayout(init: LinearLayout.() -> Unit): LinearLayout {
    return LinearLayout(this).apply {
        orientation = LinearLayout.HORIZONTAL
        init()
    }
}

inline fun ViewGroup.horizontalLayout(init: LinearLayout.() -> Unit): LinearLayout {
    val f = LinearLayout(context).apply {
        orientation = LinearLayout.HORIZONTAL
        init()
    }
    addView(f)
    return f
}

inline fun Activity.horizontalLayout(init: LinearLayout.() -> Unit): LinearLayout {
    val f = LinearLayout(this).apply {
        orientation = LinearLayout.HORIZONTAL
        init()
    }
    setContentView(f)
    return f
}