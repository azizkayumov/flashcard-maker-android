package com.kent.layouts

import android.content.Context
import android.view.ViewGroup
import android.widget.ScrollView
import androidx.core.widget.NestedScrollView

/**
 * Created by abduaziz on 2020-02-23 at 07:42.
 */

inline fun Context.scrollView(init: ScrollView.() -> Unit): ScrollView {
    return ScrollView(this).apply(init)
}

inline fun ViewGroup.scrollView(init: ScrollView.() -> Unit): ScrollView {
    val s = ScrollView(context).apply(init)
    addView(s)
    return s
}

inline fun Context.nestedScrollView(init: NestedScrollView.() -> Unit): NestedScrollView {
    return NestedScrollView(this).apply(init)
}

inline fun ViewGroup.nestedScrollView(init: NestedScrollView.() -> Unit): NestedScrollView {
    val s = NestedScrollView(context).apply(init)
    addView(s)
    return s
}