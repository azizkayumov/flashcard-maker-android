package com.kent.layouts

import android.content.Context
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat

/**
 * Created by abduaziz on 2020-02-23 at 06:29.
 */

inline fun Context.imageView(init: AppCompatImageView.() -> Unit = {}): AppCompatImageView {
    return AppCompatImageView(this).apply(init)
}

inline fun ViewGroup.imageView(init: AppCompatImageView.() -> Unit = {}): AppCompatImageView {
    val a = AppCompatImageView(context).apply(init)
    addView(a)
    return a
}

/*
* Properties
* */

fun AppCompatImageView.setIconColor(context: Context, color: Int) {
    this.setColorFilter(ContextCompat.getColor(context, color), android.graphics.PorterDuff.Mode.SRC_IN)
}

fun AppCompatImageView.setIconColor(color: Int) {
    this.setColorFilter(color, android.graphics.PorterDuff.Mode.SRC_IN)
}