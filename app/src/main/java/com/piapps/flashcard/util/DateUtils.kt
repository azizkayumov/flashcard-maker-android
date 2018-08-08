package com.piapps.flashcard.util

import com.piapps.flashcard.R
import com.piapps.flashcard.application.Flashcards
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by abduaziz on 6/10/17.
 */

object DateUtils {

    fun getHourMinute(d: Long?): String {
        val date = Date(d!!)
        val time = SimpleDateFormat("HH:mm")
        return time.format(date)
    }

    fun getHourMinuteSeconds(d: Long?): String {
        val date = Date(d!!)
        val time = SimpleDateFormat("HH:mm:ss:SSS")
        return time.format(date)
    }

    fun getDay(d: Long): String {
        val date = Date(d)
        val day = SimpleDateFormat("MMM dd")
        val year = SimpleDateFormat("yyyy")

        val dayStr = day.format(date)
        val serverDate = day.format(Date(System.currentTimeMillis()))

        //return today
        if (dayStr == serverDate)
            return Flashcards.instance.getString(R.string.today)
        //todo return yesterday if possible

        return dayStr
    }

    fun getYear(d: Long?): String {
        val date = Date(d!!)
        val year = SimpleDateFormat("yyyy")
        return year.format(date)
    }

    fun getStrictDay(d: Long?): String {
        val date = Date(d!!)
        val day = SimpleDateFormat("MMM_dd")
        return day.format(date)
    }

}
