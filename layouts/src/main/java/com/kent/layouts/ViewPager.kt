package com.kent.layouts

import android.content.Context
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2

/**
 * Created by abduaziz on 2020-02-23 at 06:31.
 */

inline fun Context.viewPager(init: ViewPager.() -> Unit = {}): ViewPager {
    return ViewPager(this).apply(init)
}

inline fun ViewGroup.viewPager(init: ViewPager.() -> Unit = {}): ViewPager {
    val v = ViewPager(context).apply(init)
    addView(v)
    return v
}

inline fun Context.viewPager2(init: ViewPager2.() -> Unit = {}): ViewPager2 {
    return ViewPager2(this).apply(init)
}

inline fun ViewGroup.viewPager2(init: ViewPager2.() -> Unit = {}): ViewPager2 {
    val v = ViewPager2(context).apply(init)
    addView(v)
    return v
}