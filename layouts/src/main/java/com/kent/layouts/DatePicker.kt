package com.kent.layouts

import android.content.Context
import android.view.ViewGroup
import android.widget.DatePicker

/**
 * Created by abduaziz on 2020-02-23 at 06:14.
 */

/*
* Date picker
*/

inline fun Context.datePicker(init: DatePicker.() -> Unit = {}): DatePicker {
    return DatePicker(this).apply(init)
}

inline fun ViewGroup.datePicker(init: DatePicker.() -> Unit = {}): DatePicker {
    val d = DatePicker(context).apply(init)
    addView(d)
    return d
}

// Should I include MaterialDatePicker which is Dialog?