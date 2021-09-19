package com.kent.layouts.viewgroup

import android.app.Activity
import android.content.Context
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout

/**
 * Created by abduaziz on 2020-02-23 at 06:40.
 */

inline fun Context.constraintLayout(init: ConstraintLayout.() -> Unit): ConstraintLayout {
    return ConstraintLayout(this).apply(init)
}

inline fun ViewGroup.constraintLayout(init: ConstraintLayout.() -> Unit): ConstraintLayout {
    val f = ConstraintLayout(context).apply(init)
    addView(f)
    return f
}

inline fun Activity.constraintLayout(init: ConstraintLayout.() -> Unit): ConstraintLayout {
    val f = ConstraintLayout(this).apply(init)
    setContentView(f)
    return f
}