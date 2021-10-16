package com.kent.layouts.viewgroup

import android.app.Activity
import android.content.Context
import android.view.ViewGroup
import android.widget.FrameLayout

/**
 * Created by abduaziz on 2020-02-23 at 06:34.
 */

inline fun Context.frameLayout(init: FrameLayout.() -> Unit): FrameLayout {
    return FrameLayout(this).apply(init)
}

inline fun ViewGroup.frameLayout(init: FrameLayout.() -> Unit): FrameLayout {
    val f = FrameLayout(context).apply(init)
    addView(f)
    return f
}

inline fun Activity.frameLayout(init: FrameLayout.() -> Unit): FrameLayout {
    val f = FrameLayout(this).apply(init)
    setContentView(f)
    return f
}