package com.ebt.newsapicleanarchitecture.common

import java.text.SimpleDateFormat
import java.util.*

object DateUtil {
    private const val DEFAULT_DATE_FORMAT = "yyyy-MM-dd"
    private const val API_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'"
    private const val NEWS_DATE_FORMAT = "dd-MM-yyyy HH:mm"

    fun getDateBeforeNowInString(dateFormat: String? = DEFAULT_DATE_FORMAT, dayAgo: Int): String {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, dayAgo)
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