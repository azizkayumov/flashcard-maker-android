package com.piapps.flashcardpro.core.extension

import android.content.Context
import android.graphics.Paint
import android.graphics.Typeface
import android.os.Build
import android.telephony.PhoneNumberUtils
import android.text.TextUtils
import android.view.ViewGroup
import android.view.ViewManager
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatImageView
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
import com.rm.freedrawview.FreeDrawView
import de.hdodenhof.circleimageview.CircleImageView
import java.lang.Exception

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

inline fun ViewGroup.circleDownloadView(init: CircleDownloadView.() -> Unit): CircleDownloadView {
    val c = CircleDownloadView(context, null).apply(init)
    addView(c)
    return c
}

inline fun ViewGroup.circleImageView(init: CircleImageView.() -> Unit): CircleImageView {
    val c = CircleImageView(context).apply(init)
    addView(c)
    return c
}

inline fun ViewGroup.autoResizeTextView(init: AutoResizeTextView.() -> Unit): AutoResizeTextView {
    val a = AutoResizeTextView(context).apply(init)
    addView(a)
    return a
}

inline fun ViewGroup.actionBar(init: ActionBar.() -> Unit): ActionBar {
    val a = ActionBar(context).apply(init)
    addView(a)
    return a
}

inline fun ViewGroup.circleProfileView(init: CircleProfileView.() -> Unit): CircleProfileView {
    val c = CircleProfileView(context).apply(init)
    addView(c)
    return c
}

inline fun ViewGroup.lineChart(init: LineChart.() -> Unit): LineChart {
    val l = LineChart(context).apply(init)
    addView(l)
    return l
}

inline fun ViewGroup.radarChart(init: RadarChart.() -> Unit): RadarChart {
    val r = RadarChart(context).apply(init)
    addView(r)
    return r
}

inline fun ViewGroup.cropImageView(init: CropImageView.() -> Unit): CropImageView {
    val c = CropImageView(context).apply(init)
    addView(c)
    return c
}

inline fun ViewGroup.drawView(init: FreeDrawView.() -> Unit): FreeDrawView {
    val d = FreeDrawView(context).apply(init)
    addView(d)
    return d
}

fun BaseFragment.toast(res: Int) {
    android.widget.Toast.makeText(this.activity, res, android.widget.Toast.LENGTH_SHORT).show()
}

fun BaseFragment.toast(s: String) {
    android.widget.Toast.makeText(this.activity, s, android.widget.Toast.LENGTH_SHORT).show()
}

fun Context.alert(init: AlertDialog.Builder.() -> Unit): AlertDialog {
    return AlertDialog.Builder(this).also(init).create()
}

fun AlertDialog.Builder.positiveButton(message: String, event: () -> Unit = {}) {
    setPositiveButton(message) { d, i ->
        event.invoke()
    }
}

fun AlertDialog.Builder.negativeButton(message: String, event: () -> Unit = {}) {
    setNegativeButton(message) { d, i ->
        event.invoke()
    }
}

fun CircleImageView.setIconColor(color: Int) {
    this.setColorFilter(color, android.graphics.PorterDuff.Mode.SRC_IN)
}