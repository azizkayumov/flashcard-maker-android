package com.piapps.flashcardpro.core.extension

import android.util.Log

/**
 * Created by abduaziz on 2019-09-22 at 00:12.
 */

val TAG = "FLASHCARDS"

fun log(l: String) {
    Log.d(TAG, l)
}

fun longLog(l: String) {
    val maxLogSize = 500
    for (i in 0..l.length / maxLogSize) {
        val start = i * maxLogSize
        var end = (i + 1) * maxLogSize
        end = if (end > l.length) l.length else end
        if (i == 0)
            Log.d(TAG, l.substring(start, end))
        else
            Log.d(TAG, "               " + l.substring(start, end))
    }
}