package com.kent.layouts

import android.content.Context
import android.graphics.Paint
import android.graphics.Typeface
import android.os.Build
import android.text.TextUtils
import android.util.TypedValue
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.google.android.material.textview.MaterialTextView

/**
 * Created by abduaziz on 2020-02-23 at 06:25.
 */

inline fun Context.textView(init: AppCompatTextView.() -> Unit = {}): AppCompatTextView {
    return AppCompatTextView(this).apply(init)
}

inline fun ViewGroup.textView(init: AppCompatTextView.() -> Unit = {}): AppCompatTextView {
    val a = AppCompatTextView(context).apply(init)
    addView(a)
    return a
}

inline fun Context.materialTextView(init: MaterialTextView.() -> Unit = {}): MaterialTextView {
    return MaterialTextView(this).apply(init)
}

inline fun ViewGroup.materialTextView(init: MaterialTextView.() -> Unit = {}): MaterialTextView {
    val a = MaterialTextView(context).apply(init)
    addView(a)
    return a
}

/*
* Properties
* */

var TextView.singleLine: Boolean
    get() = false
    set(v) = setSingleLine(v)

var TextView.lines: Int
    get() = 0
    set(v) = setLines(v)

var TextView.allCaps: Boolean
    get() = false
    inline set(value) = setAllCaps(value)

var TextView.ems: Int
    get() = noGetter()
    inline set(value) = setEms(value)

inline var TextView.isSelectable: Boolean
    get() = isTextSelectable
    set(value) = setTextIsSelectable(value)

var TextView.textAppearance: Int
    get() = noGetter()
    set(value) = if (Build.VERSION.SDK_INT >= 23) setTextAppearance(value) else setTextAppearance(context, value)

var TextView.textSizeDimen: Int
    get() = noGetter()
    set(value) = setTextSize(TypedValue.COMPLEX_UNIT_PX, context.resources.getDimension(value))

var TextView.textColorResource: Int
    get() = noGetter()
    set(colorId) = setTextColor(ContextCompat.getColor(context, colorId))

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

fun TextView.hintColorResource(res: Int) {
    setHintTextColor(ContextCompat.getColor(context, res))
}