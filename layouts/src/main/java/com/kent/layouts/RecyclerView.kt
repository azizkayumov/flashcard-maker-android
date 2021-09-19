package com.kent.layouts

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by abduaziz on 2020-02-23 at 06:30.
 */

inline fun Context.recyclerView(init: RecyclerView.() -> Unit = {}): RecyclerView {
    return RecyclerView(this).apply(init)
}

inline fun ViewGroup.recyclerView(init: RecyclerView.() -> Unit = {}): RecyclerView {
    val r = RecyclerView(context).apply(init)
    addView(r)
    return r
}