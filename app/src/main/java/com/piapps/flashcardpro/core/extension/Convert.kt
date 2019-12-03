package com.piapps.flashcardpro.core.extension

import android.os.Build
import android.telephony.PhoneNumberUtils
import android.text.Html
import android.text.Spanned

/**
 * Created by abduaziz on 2019-09-22 at 00:11.
 */

fun String.formatPhone(): String {
    var r = ""
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        r = "+${PhoneNumberUtils.formatNumber(this.toString(), "UZ")}"
    } else {
        r = "+${PhoneNumberUtils.formatNumber(this.toString())}"
    }
    if (r.equals("+null")) return this
    return r
}

fun String.fromHtml(): Spanned {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(this, 0)
    } else {
        Html.fromHtml(this)
    }
}