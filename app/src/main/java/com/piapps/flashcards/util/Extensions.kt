package com.piapps.flashcards.util

import android.graphics.Color
import android.support.v4.content.ContextCompat
import com.piapps.flashcards.R
import com.piapps.flashcards.application.Flashcards

/**
 * Created by abduaziz on 4/27/18.
 */

object Extensions {

    fun color(id: Long) = when (id % 18) {
        0L -> ContextCompat.getColor(Flashcards.instance.applicationContext, R.color.c18)
        1L -> ContextCompat.getColor(Flashcards.instance.applicationContext, R.color.c1)
        2L -> ContextCompat.getColor(Flashcards.instance.applicationContext, R.color.c2)
        3L -> ContextCompat.getColor(Flashcards.instance.applicationContext, R.color.c3)
        4L -> ContextCompat.getColor(Flashcards.instance.applicationContext, R.color.c4)
        5L -> ContextCompat.getColor(Flashcards.instance.applicationContext, R.color.c5)
        6L -> ContextCompat.getColor(Flashcards.instance.applicationContext, R.color.c6)
        7L -> ContextCompat.getColor(Flashcards.instance.applicationContext, R.color.c7)
        8L -> ContextCompat.getColor(Flashcards.instance.applicationContext, R.color.c8)
        9L -> ContextCompat.getColor(Flashcards.instance.applicationContext, R.color.c9)
        10L -> ContextCompat.getColor(Flashcards.instance.applicationContext, R.color.c10)
        11L -> ContextCompat.getColor(Flashcards.instance.applicationContext, R.color.c11)
        12L -> ContextCompat.getColor(Flashcards.instance.applicationContext, R.color.c12)
        13L -> ContextCompat.getColor(Flashcards.instance.applicationContext, R.color.c13)
        14L -> ContextCompat.getColor(Flashcards.instance.applicationContext, R.color.c14)
        15L -> ContextCompat.getColor(Flashcards.instance.applicationContext, R.color.c15)
        16L -> ContextCompat.getColor(Flashcards.instance.applicationContext, R.color.c16)
        17L -> ContextCompat.getColor(Flashcards.instance.applicationContext, R.color.c17)
        else -> ContextCompat.getColor(Flashcards.instance.applicationContext, R.color.c1)
    }

    fun colorDarker(color: Int): Int? {
        val index = setColors().indexOfFirst { it == color }
        if (index >= 0)
            return setDarkerColors()[index]
        else return null
    }

    fun setColors(): IntArray {
        val colors = IntArray(18)
        colors[0] = ContextCompat.getColor(Flashcards.instance.applicationContext, R.color.c18)
        colors[1] = ContextCompat.getColor(Flashcards.instance.applicationContext, R.color.c1)
        colors[2] = ContextCompat.getColor(Flashcards.instance.applicationContext, R.color.c2)
        colors[3] = ContextCompat.getColor(Flashcards.instance.applicationContext, R.color.c3)
        colors[4] = ContextCompat.getColor(Flashcards.instance.applicationContext, R.color.c4)
        colors[5] = ContextCompat.getColor(Flashcards.instance.applicationContext, R.color.c5)
        colors[6] = ContextCompat.getColor(Flashcards.instance.applicationContext, R.color.c6)
        colors[7] = ContextCompat.getColor(Flashcards.instance.applicationContext, R.color.c7)
        colors[8] = ContextCompat.getColor(Flashcards.instance.applicationContext, R.color.c8)
        colors[9] = ContextCompat.getColor(Flashcards.instance.applicationContext, R.color.c9)
        colors[10] = ContextCompat.getColor(Flashcards.instance.applicationContext, R.color.c10)
        colors[11] = ContextCompat.getColor(Flashcards.instance.applicationContext, R.color.c11)
        colors[12] = ContextCompat.getColor(Flashcards.instance.applicationContext, R.color.c12)
        colors[13] = ContextCompat.getColor(Flashcards.instance.applicationContext, R.color.c13)
        colors[14] = ContextCompat.getColor(Flashcards.instance.applicationContext, R.color.c14)
        colors[15] = ContextCompat.getColor(Flashcards.instance.applicationContext, R.color.c15)
        colors[16] = ContextCompat.getColor(Flashcards.instance.applicationContext, R.color.c16)
        colors[17] = ContextCompat.getColor(Flashcards.instance.applicationContext, R.color.c17)
        return colors
    }

    fun setDarkerColors(): IntArray {
        val colors = IntArray(18)
        colors[0] = ContextCompat.getColor(Flashcards.instance.applicationContext, R.color.c18dark)
        colors[1] = ContextCompat.getColor(Flashcards.instance.applicationContext, R.color.c1dark)
        colors[2] = ContextCompat.getColor(Flashcards.instance.applicationContext, R.color.c2dark)
        colors[3] = ContextCompat.getColor(Flashcards.instance.applicationContext, R.color.c3dark)
        colors[4] = ContextCompat.getColor(Flashcards.instance.applicationContext, R.color.c4dark)
        colors[5] = ContextCompat.getColor(Flashcards.instance.applicationContext, R.color.c5dark)
        colors[6] = ContextCompat.getColor(Flashcards.instance.applicationContext, R.color.c6dark)
        colors[7] = ContextCompat.getColor(Flashcards.instance.applicationContext, R.color.c7dark)
        colors[8] = ContextCompat.getColor(Flashcards.instance.applicationContext, R.color.c8dark)
        colors[9] = ContextCompat.getColor(Flashcards.instance.applicationContext, R.color.c9dark)
        colors[10] = ContextCompat.getColor(Flashcards.instance.applicationContext, R.color.c10dark)
        colors[11] = ContextCompat.getColor(Flashcards.instance.applicationContext, R.color.c11dark)
        colors[12] = ContextCompat.getColor(Flashcards.instance.applicationContext, R.color.c12dark)
        colors[13] = ContextCompat.getColor(Flashcards.instance.applicationContext, R.color.c13dark)
        colors[14] = ContextCompat.getColor(Flashcards.instance.applicationContext, R.color.c14dark)
        colors[15] = ContextCompat.getColor(Flashcards.instance.applicationContext, R.color.c15dark)
        colors[16] = ContextCompat.getColor(Flashcards.instance.applicationContext, R.color.c16dark)
        colors[17] = ContextCompat.getColor(Flashcards.instance.applicationContext, R.color.c17dark)
        return colors
    }

}

fun Int.toHexColor(): String {
    return String.format("#%06X", 0xFFFFFF and this)
}

fun String.toColor(): Int {
    if (this.length == 0) return Color.BLACK
    return Color.parseColor(this)
}