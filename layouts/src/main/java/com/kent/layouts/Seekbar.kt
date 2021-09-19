package com.kent.layouts

import android.content.Context
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatSeekBar

/**
 * Created by abduaziz on 2020-02-23 at 06:32.
 */

inline fun Context.seekBar(init: AppCompatSeekBar.() -> Unit = {}): AppCompatSeekBar {
    return AppCompatSeekBar(this).apply(init)
}

inline fun ViewGroup.seekBar(init: AppCompatSeekBar.() -> Unit = {}): AppCompatSeekBar {
    val a = AppCompatSeekBar(context).apply(init)
    addView(a)
    return a
}