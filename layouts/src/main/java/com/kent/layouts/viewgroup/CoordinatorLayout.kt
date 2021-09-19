package com.kent.layouts.viewgroup

import android.app.Activity
import android.content.Context
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout

/**
 * Created by abduaziz on 2020-02-23 at 07:50.
 */


inline fun Context.coordinatorLayout(init: CoordinatorLayout.() -> Unit): CoordinatorLayout {
    return CoordinatorLayout(this).apply(init)
}

inline fun ViewGroup.coordinatorLayout(init: CoordinatorLayout.() -> Unit): CoordinatorLayout {
    val f = CoordinatorLayout(context).apply(init)
    addView(f)
    return f
}

inline fun Activity.coordinatorLayout(init: CoordinatorLayout.() -> Unit): CoordinatorLayout {
    val f = CoordinatorLayout(this).apply(init)
    setContentView(f)
    return f
}