package com.kent.layouts.viewgroup

import android.view.ViewGroup

/**
 * Created by abduaziz on 2020-02-24 at 06:30.
 */

fun ViewGroup.lparams(width: Int, height: Int) {
    layoutParams = ViewGroup.LayoutParams(width, height)
}