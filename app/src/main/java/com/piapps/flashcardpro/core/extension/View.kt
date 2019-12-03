package com.piapps.flashcardpro.core.extension

import android.content.Context
import android.graphics.Paint
import android.graphics.Typeface
import android.os.Build
import android.support.design.widget.FloatingActionButton
import android.support.v4.content.ContextCompat
import android.support.v7.widget.AppCompatImageView
import android.telephony.PhoneNumberUtils
import android.text.TextUtils
import android.util.TypedValue
import android.view.View
import android.view.ViewManager
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.charts.RadarChart
import com.isseiaoki.simplecropview.CropImageView
import com.piapps.flashcardpro.core.platform.BaseFragment
import com.piapps.flashcardpro.core.platform.component.ActionBar
import com.piapps.flashcardpro.core.platform.component.AutoResizeTextView
import com.piapps.flashcardpro.core.platform.component.CircleDownloadView
import com.piapps.flashcardpro.core.platform.component.CircleProfileView
import com.piapps.flashcardpro.core.platform.component.viewpager.HorizontalScrollIndicator
import com.rm.freedrawview.FreeDrawView
import de.hdodenhof.circleimageview.CircleImageView
import org.jetbrains.anko.AnkoViewDslMarker
import org.jetbrains.anko.custom.ankoView

/**
 * Created by abduaziz on 2019-09-22 at 00:13.
 */

fun BaseFragment.getString(resource: Int): String {
    return ctx.getString(resource)
}

fun ImageView.loadFromUrl(url: String) =
    Glide.with(this.context.applicationContext)
        .load(url)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)

fun ImageView.loadFromRes(res: Int) =
    Glide.with(this.context.applicationContext)
        .load(res)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)

fun ImageView.load(any: Any) =
    Glide.with(this.context.applicationContext)
        .load(any)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)

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

fun TextView.setStyle(context: Context, resId: Int) {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
        this.setTextAppearance(context, resId)
    } else {
        this.setTextAppearance(resId)
    }
}

// if text does not fit, it shortens the text and addCall "..." at the end
fun TextView.ellipsize(mLines: Int = 4) {
    maxLines = mLines
    ellipsize = TextUtils.TruncateAt.END
}

fun TextView.makeBold() {
    setTypeface(typeface, Typeface.BOLD)
}

fun TextView.makeItalic() {
    setTypeface(typeface, Typeface.ITALIC)
}

fun TextView.underline() {
    paintFlags = paintFlags or Paint.UNDERLINE_TEXT_FLAG
}

fun TextView.formatPhone() {
    val t = text
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        text = "+${PhoneNumberUtils.formatNumber(t.toString(), "UZ")}"
    } else {
        text = "+${PhoneNumberUtils.formatNumber(t.toString())}"
    }
    if (text.equals("+null")) text = t
}

fun AppCompatImageView.setIconColor(context: Context, color: Int) {
    this.setColorFilter(ContextCompat.getColor(context, color), android.graphics.PorterDuff.Mode.SRC_IN)
}

fun AppCompatImageView.setIconColorWithRealColor(color: Int) {
    this.setColorFilter(color, android.graphics.PorterDuff.Mode.SRC_IN)
}

fun FloatingActionButton.setIconColor(context: Context, color: Int) {
    this.setColorFilter(ContextCompat.getColor(context, color), android.graphics.PorterDuff.Mode.SRC_IN)
}

inline fun ViewManager.appCompatImageView(init: (@AnkoViewDslMarker AppCompatImageView).() -> Unit): AppCompatImageView {
    return ankoView({ AppCompatImageView(it, null) }, theme = 0) { init() }
}

inline fun ViewManager.circleDownloadView(init: (@AnkoViewDslMarker CircleDownloadView).() -> Unit): CircleDownloadView {
    return ankoView({ CircleDownloadView(it, null) }, theme = 0) { init() }
}

inline fun ViewManager.circleImageView(init: (@AnkoViewDslMarker CircleImageView).() -> Unit): CircleImageView {
    return ankoView({ CircleImageView(it, null) }, theme = 0) { init() }
}

inline fun ViewManager.autoResizeTextView(init: (@AnkoViewDslMarker AutoResizeTextView).() -> Unit): AutoResizeTextView {
    return ankoView({ AutoResizeTextView(it, null) }, theme = 0) { init() }
}

inline fun ViewManager.actionBar(init: (@AnkoViewDslMarker ActionBar).() -> Unit): ActionBar {
    return ankoView({ ActionBar(it) }, theme = 0) { init() }
}

inline fun ViewManager.circleProfileView(init: (@AnkoViewDslMarker CircleProfileView).() -> Unit): CircleProfileView {
    return ankoView({ CircleProfileView(it) }, theme = 0) { init() }
}

inline fun ViewManager.recyclerIndicatorLayout(init: (@AnkoViewDslMarker HorizontalScrollIndicator).() -> Unit): HorizontalScrollIndicator {
    return ankoView({ HorizontalScrollIndicator(it) }, theme = 0) { init() }
}

inline fun ViewManager.lineChart(init: (@AnkoViewDslMarker LineChart).() -> Unit): LineChart {
    return ankoView({ LineChart(it) }, theme = 0) { init() }
}

inline fun ViewManager.radarChart(init: (@AnkoViewDslMarker RadarChart).() -> Unit): RadarChart {
    return ankoView({ RadarChart(it) }, theme = 0) { init() }
}

inline fun ViewManager.cropImageView(init: (@AnkoViewDslMarker CropImageView).() -> Unit): CropImageView {
    return ankoView({ CropImageView(it) }, theme = 0) { init() }
}

inline fun ViewManager.drawView(init: (@AnkoViewDslMarker FreeDrawView).() -> Unit): FreeDrawView {
    return ankoView({ FreeDrawView(it) }, theme = 0) { init() }
}

fun BaseFragment.toast(res: Int) {
    android.widget.Toast.makeText(this.activity, res, android.widget.Toast.LENGTH_SHORT).show()
}

fun BaseFragment.toast(s: String) {
    android.widget.Toast.makeText(this.activity, s, android.widget.Toast.LENGTH_SHORT).show()
}