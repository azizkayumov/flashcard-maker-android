package com.piapps.flashcardpro.core.settings

import android.content.Context

/**
 * Created by abduaziz on 2019-09-22 at 00:10.
 */

object Prefs {
    private val prefs = "APP_PREFS"

    val USER_LOGGED_IN = "USER_LOGGED_IN"

    val LANGUAGE = "language"
    val LANGUAGE_ENGLISH = "en"
    val LANGUAGE_RUSSIAN = "ru"
    val LANGUAGE_KOREAN = "ko"
    val LANGUAGE_UZBEK = "uz"

    val COLOR_CARD_BACKGROUND = "color_card_background"
    val COLOR_CARD_TEXT = "color_card_text"
    val SIZE_CARD_TEXT = "size_card_text"

    val THEME_ID = "THEME_ID"

    val USER_FIRST_TIME = "is_user_first_time"

    fun save(context: Context?, key: String, value: String) {
        var prefs = context?.getSharedPreferences(prefs, Context.MODE_PRIVATE)
        var editor = prefs?.edit()
        editor?.putString(key, value)
        editor?.apply()
    }

    fun save(context: Context?, key: String, value: Float) {
        var prefs = context?.getSharedPreferences(prefs, Context.MODE_PRIVATE)
        var editor = prefs?.edit()
        editor?.putFloat(key, value)
        editor?.apply()
    }

    fun save(context: Context?, key: String, value: Long) {
        var prefs = context?.getSharedPreferences(prefs, Context.MODE_PRIVATE)
        var editor = prefs?.edit()
        editor?.putLong(key, value)
        editor?.apply()
    }

    fun save(context: Context?, key: String, value: Int) {
        var prefs = context?.getSharedPreferences(prefs, Context.MODE_PRIVATE)
        var editor = prefs?.edit()
        editor?.putInt(key, value)
        editor?.apply()
    }

    fun save(context: Context?, key: String, value: Boolean) {
        var prefs = context?.getSharedPreferences(prefs, Context.MODE_PRIVATE)
        var editor = prefs?.edit()
        editor?.putBoolean(key, value)
        editor?.apply()
    }

    fun get(context: Context?, key: String, default: String): String {
        var prefs = context?.getSharedPreferences(prefs, Context.MODE_PRIVATE)
        return prefs?.getString(key, default).toString()
    }

    fun get(context: Context?, key: String, default: Float): Float {
        var prefs = context?.getSharedPreferences(prefs, Context.MODE_PRIVATE)
        return prefs!!.getFloat(key, default)
    }

    fun get(context: Context?, key: String, default: Long): Long {
        var prefs = context?.getSharedPreferences(prefs, Context.MODE_PRIVATE)
        if (prefs == null) return default
        return prefs.getLong(key, default)
    }

    fun get(context: Context?, key: String, default: Int): Int {
        var prefs = context?.getSharedPreferences(prefs, Context.MODE_PRIVATE)
        if (prefs == null) return default
        return prefs.getInt(key, default)
    }

    fun get(context: Context?, key: String, default: Boolean): Boolean {
        var prefs = context?.getSharedPreferences(prefs, Context.MODE_PRIVATE)
        return prefs!!.getBoolean(key, default)
    }

    fun clear(context: Context?) {
        val sharedPreferences = context?.getSharedPreferences(prefs, Context.MODE_PRIVATE)
        val editor = sharedPreferences?.edit()
        editor?.clear()
        editor?.apply()
    }
}