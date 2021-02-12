package com.piapps.flashcardpro.core.extension

import android.graphics.Color
import com.piapps.flashcardpro.R
import java.util.*
import kotlin.math.abs

/**
 * Created by abduaziz on 4/27/18.
 */

fun Long.color() = when (abs(this) % 16) {
    0L -> R.color.c15
    1L -> R.color.c1
    2L -> R.color.c2
    3L -> R.color.c3
    4L -> R.color.c4
    5L -> R.color.c5
    6L -> R.color.c6
    7L -> R.color.c7
    8L -> R.color.c8
    9L -> R.color.c9
    10L -> R.color.c10
    11L -> R.color.c11
    12L -> R.color.c12
    13L -> R.color.c13
    14L -> R.color.c14
    15L -> R.color.c15
    else -> R.color.c1
}

fun Int.toHexColor(): String {
    return String.format("#%06X", 0xFFFFFF and this)
}

fun String.toColor(): Int {
    if (this.isEmpty()) return Color.BLACK
    return Color.parseColor(this)
}

fun IntRange.random() =
    Random().nextInt((endInclusive + 1) - start) + start