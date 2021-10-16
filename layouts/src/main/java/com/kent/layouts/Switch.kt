package com.kent.layouts

import android.content.Context
import android.view.ViewGroup
import androidx.appcompat.widget.SwitchCompat
import com.google.android.material.switchmaterial.SwitchMaterial

/**
 * Created by abduaziz on 2020-02-23 at 06:20.
 */

inline fun Context.switch(init: SwitchCompat.() -> Unit = {}): SwitchCompat {
    return SwitchCompat(this).apply(init)
}

inline fun ViewGroup.switch(init: SwitchCompat.() -> Unit = {}): SwitchCompat {
    val s = SwitchCompat(context).apply(init)
    addView(s)
    return s
}

inline fun Context.switchMaterial(init: SwitchMaterial.() -> Unit = {}): SwitchMaterial {
    return SwitchMaterial(this).apply(init)
}

inline fun ViewGroup.switchMaterial(init: SwitchMaterial.() -> Unit = {}): SwitchMaterial {
    val s = SwitchMaterial(context).apply(init)
    addView(s)
    return s
}