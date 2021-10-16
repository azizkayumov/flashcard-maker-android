package com.kent.layouts

import android.graphics.drawable.Drawable
import android.os.Build
import android.util.TypedValue
import android.view.View
import android.widget.FrameLayout
import android.widget.GridLayout
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.bottomappbar.BottomAppBar

/**
 * Created by abduaziz on 2020-02-22 at 05:54.
 */

fun noGetter() = 0

var View.backgroundDrawable: Drawable?
    inline get() = background
    set(value) = setBackgroundDrawable(value)

var View.backgroundColorResource: Int
    get() = noGetter()
    set(colorId) = setBackgroundColor(ContextCompat.getColor(context, colorId))

var View.leftPadding: Int
    inline get() = paddingLeft
    set(value) = setPadding(value, paddingTop, paddingRight, paddingBottom)

var View.topPadding: Int
    inline get() = paddingTop
    set(value) = setPadding(paddingLeft, value, paddingRight, paddingBottom)

var View.rightPadding: Int
    inline get() = paddingRight
    set(value) = setPadding(paddingLeft, paddingTop, value, paddingBottom)

var View.bottomPadding: Int
    inline get() = paddingBottom
    set(value) = setPadding(paddingLeft, paddingTop, paddingRight, value)

var View.horizontalPadding: Int
    inline get() = paddingTop
    set(value) = setPadding(value, paddingTop, value, paddingBottom)

var View.verticalPadding: Int
    inline get() = paddingRight
    set(value) = setPadding(paddingLeft, value, paddingRight, value)

var View.padding: Int
    inline get() = paddingTop
    inline set(value) = setPadding(value, value, value, value)

var AppBarLayout.LayoutParams.margin: Int
    inline get() = marginEnd
    inline set(value) = setMargins(value, value, value, value)

var CollapsingToolbarLayout.LayoutParams.margin: Int
    inline get() = marginEnd
    inline set(value) = setMargins(value, value, value, value)

var ConstraintLayout.LayoutParams.margin: Int
    inline get() = marginEnd
    inline set(value) = setMargins(value, value, value, value)

var CoordinatorLayout.LayoutParams.margin: Int
    inline get() = marginEnd
    inline set(value) = setMargins(value, value, value, value)

var DrawerLayout.LayoutParams.margin: Int
    inline get() = marginEnd
    inline set(value) = setMargins(value, value, value, value)

var FrameLayout.LayoutParams.margin: Int
    inline get() = marginEnd
    inline set(value) = setMargins(value, value, value, value)

var GridLayout.LayoutParams.margin: Int
    inline get() = marginEnd
    inline set(value) = setMargins(value, value, value, value)

var LinearLayout.LayoutParams.margin: Int
    inline get() = marginEnd
    inline set(value) = setMargins(value, value, value, value)

var RelativeLayout.LayoutParams.margin: Int
    inline get() = marginEnd
    inline set(value) = setMargins(value, value, value, value)

fun View.setRippleEffect() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        val outValue = TypedValue()
        context.theme.resolveAttribute(android.R.attr.selectableItemBackground, outValue, true)
        setBackgroundResource(outValue.resourceId)
        isClickable = true
    }
}

fun View.setRippleEffectBorderless() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        val outValue = TypedValue()
        context.theme.resolveAttribute(android.R.attr.selectableItemBackgroundBorderless, outValue, true)
        setBackgroundResource(outValue.resourceId)
        isClickable = true
    }
}
