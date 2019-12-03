package com.piapps.flashcardpro.core.settings

import android.content.Context
import com.piapps.flashcardpro.core.platform.theme.Theme
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by abduaziz on 2019-09-22 at 00:37.
 */

@Singleton
class Settings
@Inject constructor(private val context: Context) {

    fun userLoggedIn() = true // Prefs.get(context, Prefs.USER_LOGGED_IN, false)

    fun isUserFirstTime() = Prefs.get(context, Prefs.USER_FIRST_TIME, true)
    fun setUserNotFirstTime() = Prefs.save(context, Prefs.USER_FIRST_TIME, false)

    fun setUserLoggedIn(loggedIn: Boolean) = Prefs.save(context, Prefs.USER_LOGGED_IN, loggedIn)

    fun language() = Prefs.get(context, Prefs.LANGUAGE, Prefs.LANGUAGE_ENGLISH)
    fun setLanguage(lang: String) = Prefs.save(context, Prefs.LANGUAGE, lang)

    fun setTheme(id: Long) = Prefs.save(context, Prefs.THEME_ID, id)
    fun getTheme() = Theme.getTheme(Prefs.get(context, Prefs.THEME_ID, Theme.THEME_CLASSIC))

    fun clear() = Prefs.clear(context)
}