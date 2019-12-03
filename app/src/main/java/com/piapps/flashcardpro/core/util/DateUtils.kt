package com.piapps.flashcardpro.core.util

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {

    fun getBirthDay(d: Long?): String {
        if (d == null) return ""
        val date = Date(d)
        val day = SimpleDateFormat("yyyy-MM-dd")
        //return today
        return day.format(date)
    }

    fun getHourMinute(d: Long?): String {
        if (d == null) return ""
        if (d < 1511982000000) return ""
        val date = Date(d)
        val time = SimpleDateFormat("HH:mm")
        return time.format(date)
    }

    fun getHourMinuteSeconds(d: Long?): String {
        if (d == null) return ""
        if (d < 1511982000000) return ""
        val date = Date(d!!)
        val time = SimpleDateFormat("HH:mm:ss:SSS")
        return time.format(date)
    }

    fun getYear(d: Long?): String {
        if (d == null) return ""
        if (d < 1511982000000) return ""
        val date = Date(d!!)
        val year = SimpleDateFormat("yyyy")
        return year.format(date)
    }

    fun getStrictDay(d: Long?): String {
        if (d == null) return ""
        if (d < 1511982000000) return ""
        val date = Date(d!!)
        val day = SimpleDateFormat("MMM_dd")
        return day.format(date)
    }

    fun getDay(d: Long?): Int {
        if (d == null) return -1
        if (d < 1511982000000) return -1
        val date = Date(d)
        val day = SimpleDateFormat("dd")
        return day.format(date).toInt()
    }

    fun getYearMonthDay(d: Long?): String {
        if (d == null) return ""
        if (d < 1511982000000) return ""
        val date = Date(d)
        val day = SimpleDateFormat("yyyy_MMM_dd")
        return day.format(date)
    }

    fun getMonthDay(d: Long?): String {
        if (d == null) return ""
        if (d < 1511982000000) return ""
        val date = Date(d)
        val day = SimpleDateFormat("MM / dd")
        return day.format(date)
    }

    fun getMonthDayHourMinute(d: Long?): String {
        if (d == null) return ""
        if (d < 1511982000000) return ""
        val date = Date(d)
        val day = SimpleDateFormat("MMM dd, HH:mm")
        return day.format(date)
    }

    fun getMonthDayYearHourMinute(d: Long?): String {
        if (d == null) return ""
        val date = Date(d)
        val day = SimpleDateFormat("MMM dd, yyyy HH:mm")
        return day.format(date)
    }

    fun getFilenameFromDate(extension: String): String {
        val now = System.currentTimeMillis()
        return getYear(now) + "_" + getStrictDay(
            now
        ) + "_" + getHourMinuteSeconds(now) + extension
    }

}
