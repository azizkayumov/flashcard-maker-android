package com.kent.layouts

import android.content.Context
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatRatingBar

/**
 * Created by abduaziz on 2020-02-23 at 06:30.
 */

inline fun Context.ratingBar(init: AppCompatRatingBar.() -> Unit = {}): AppCompatRatingBar {
    return AppCompatRatingBar(this).apply(init)
}

inline fun ViewGroup.ratingBar(init: AppCompatRatingBar.() -> Unit = {}): AppCompatRatingBar {
    val r = AppCompatRatingBar(context).apply(init)
    addView(r)
    return r
}