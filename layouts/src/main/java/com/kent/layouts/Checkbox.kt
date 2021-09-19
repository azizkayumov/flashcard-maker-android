package com.kent.layouts

import android.content.Context
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatCheckBox
import com.google.android.material.checkbox.MaterialCheckBox

/**
 * Created by abduaziz on 2020-02-23 at 06:09.
 */

inline fun Context.checkBox(init: AppCompatCheckBox.() -> Unit = {}): AppCompatCheckBox {
    return AppCompatCheckBox(this).apply(init)
}

inline fun ViewGroup.checkBox(init: AppCompatCheckBox.() -> Unit = {}): AppCompatCheckBox {
    val a = AppCompatCheckBox(context).apply(init)
    addView(a)
    return a
}

inline fun Context.materialCheckBox(init: MaterialCheckBox.() -> Unit = {}): MaterialCheckBox {
    return MaterialCheckBox(this).apply(init)
}

inline fun ViewGroup.materialCheckBox(init: MaterialCheckBox.() -> Unit = {}): MaterialCheckBox {
    val a = MaterialCheckBox(context).apply(init)
    addView(a)
    return a
}