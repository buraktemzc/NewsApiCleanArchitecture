package com.ebt.newsapicleanarchitecture.common

import java.text.SimpleDateFormat
import java.util.*

object DateUtil {
    const val DEFAULT_DATE_FORMAT = "yyyy-MM-dd"
    const val API_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'"
    const val NEWS_DATE_FORMAT = "dd-MM-yyyy HH:mm"

    fun getDateBeforeNow(dayAgo: Int): Calendar {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, dayAgo)
        return calendar
    }

    fun getDateBeforeNowInString(dateFormat: String? = DEFAULT_DATE_FORMAT, dayAgo: Int): String {
        val calendar = getDateBeforeNow(dayAgo)
        return SimpleDateFormat(dateFormat).format(calendar.time)
    }

    fun getDateInString(dateFormat: String? = DEFAULT_DATE_FORMAT, calendar: Calendar): String {
        return SimpleDateFormat(dateFormat).format(calendar.time)
    }

    fun convertISODateToString(
        dateFormat: String? = API_DATE_FORMAT,
        targetDateFormat: String? = NEWS_DATE_FORMAT,
        date: String
    ): String {
        val parsedDate = SimpleDateFormat(dateFormat).parse(date)
        return SimpleDateFormat(targetDateFormat).format(parsedDate)
    }
}