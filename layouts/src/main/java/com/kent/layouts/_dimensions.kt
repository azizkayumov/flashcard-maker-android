package com.kent.layouts

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DimenRes

/**
 * Created by abduaziz on 2020-02-22 at 06:14.
 */

val matchParent: Int
    get() = ViewGroup.LayoutParams.MATCH_PARENT

val wrapContent: Int
    get() = ViewGroup.LayoutParams.WRAP_CONTENT

//returns dip(dp) dimension value in pixels
fun Context.dip(value: Int): Int = (value * resources.displayMetrics.density).toInt()

fun Context.dip(value: Float): Int = (value * resources.displayMetrics.density).toInt()

//return sp dimension value in pixels
fun Context.sp(value: Int): Int = (value * resources.displayMetrics.scaledDensity).toInt()

fun Context.sp(value: Float): Int = (value * resources.displayMetrics.scaledDensity).toInt()

//converts px value into dip or sp
fun Context.px2dip(px: Int): Float = px.toFloat() / resources.displayMetrics.density

fun Context.px2sp(px: Int): Float = px.toFloat() / resources.displayMetrics.scaledDensity

fun Context.dimen(@DimenRes resource: Int): Int = resources.getDimensionPixelSize(resource)

fun View.dip(value: Int): Int = context.dip(value)

fun View.dip(value: Float): Int = context.dip(value)

fun View.sp(value: Int): Int = context.sp(value)
fun View.sp(value: Float): Int = context.sp(value)

fun View.px2dip(px: Int): Float = context.px2dip(px)
fun View.px2sp(px: Int): Float = context.px2sp(px)

fun View.dimen(@DimenRes resource: Int): Int = context.dimen(resource)
