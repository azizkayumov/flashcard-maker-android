package com.kent.layouts

import android.content.Context
import android.view.ViewGroup
import android.widget.RadioGroup
import androidx.appcompat.widget.AppCompatRadioButton
import com.google.android.material.radiobutton.MaterialRadioButton

/**
 * Created by abduaziz on 2020-02-23 at 06:17.
 */

inline fun Context.radioButton(init: AppCompatRadioButton.() -> Unit = {}): AppCompatRadioButton {
    return AppCompatRadioButton(this).apply(init)
}

inline fun ViewGroup.radioButton(init: AppCompatRadioButton.() -> Unit = {}): AppCompatRadioButton {
    val a = AppCompatRadioButton(context).apply(init)
    addView(a)
    return a
}

inline fun Context.radioGroup(init: RadioGroup.() -> Unit = {}): RadioGroup {
    return RadioGroup(this).apply(init)
}

inline fun ViewGroup.radioGroup(init: RadioGroup.() -> Unit = {}): RadioGroup {
    val r = RadioGroup(context).apply(init)
    addView(r)
    return r
}

inline fun Context.materialRadioButton(init: MaterialRadioButton.() -> Unit = {}): MaterialRadioButton {
    return MaterialRadioButton(this).apply(init)
}

inline fun ViewGroup.materialRadioButton(init: MaterialRadioButton.() -> Unit = {}): MaterialRadioButton {
    val a = MaterialRadioButton(context).apply(init)
    addView(a)
    return a
}