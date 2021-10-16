package com.kent.layouts

import android.content.Context
import android.widget.Toast

/**
 * Created by abduaziz on 2020-02-24 at 06:05.
 */

fun Context.toast(s: String) {
    Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
}