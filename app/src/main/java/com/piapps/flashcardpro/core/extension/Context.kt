package com.piapps.flashcardpro.core.extension

import android.content.Context
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.piapps.flashcardpro.core.platform.theme.Theme
import com.piapps.flashcardpro.core.settings.Prefs
import java.util.*

/**
 * Created by abduaziz on 2019-09-22 at 00:09.
 */

fun Context.getLocalizedString(resource: Int): String {
    val configuration = Configuration(resources.configuration)
    val language = Prefs.get(this, Prefs.LANGUAGE, Prefs.LANGUAGE_ENGLISH)
    configuration.setLocale(Locale(language))
    return createConfigurationContext(configuration).resources.getString(resource)
}

fun Context.getLocalizedString(resource: Int, formatArgs: Array<String>): String {
    val configuration = Configuration(resources.configuration)
    val language = Prefs.get(this, Prefs.LANGUAGE, Prefs.LANGUAGE_ENGLISH)
    configuration.setLocale(Locale(language))
    // this is temporary solution, find better later
    if (formatArgs.size == 1)
        return createConfigurationContext(configuration).resources.getString(resource, formatArgs[0])
    if (formatArgs.size == 2)
        return createConfigurationContext(configuration).resources.getString(resource, formatArgs[0], formatArgs[1])
    return createConfigurationContext(configuration).resources.getString(resource, formatArgs)
}

fun Context.appTheme() = Theme.getTheme(Prefs.get(this, Prefs.THEME_ID, Theme.THEME_CLASSIC))