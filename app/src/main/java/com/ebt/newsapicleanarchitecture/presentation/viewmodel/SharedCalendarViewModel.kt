package com.ebt.newsapicleanarchitecture.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ebt.newsapicleanarchitecture.common.Event
import com.ebt.newsapicleanarchitecture.presentation.dialog.CalendarDate
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject

@HiltViewModel
class SharedCalendarViewModel @Inject constructor() : ViewModel() {
    private val _calendar = MutableLiveData<Event<CalendarDate>>()
    val calendar: LiveData<Event<CalendarDate>>
        get() = _calendar

    var sCalendar: Calendar? = Calendar.getInstance()
    var eCalendar: Calendar? = Calendar.getInstance()

    fun changeCalendar() {
        val calendarDate = CalendarDate(sCalendar!!, eCalendar!!)
        _calendar.value = Event(calendarDate)
    }

    fun isDateRangeSelectedCorrectly(): Boolean {
        return sCalendar!!.timeInMillis < eCalendar!!.timeInMillis
    }

    fun changeStartCalendar(day: Int, month: Int, year: Int) {
        sCalendar = createCalendar(day, month, year, false)
    }

    fun changeEndCalendar(day: Int, month: Int, year: Int) {
        eCalendar = createCalendar(day, month, year, true)
    }

    private fun createCalendar(day: Int, month: Int, year: Int, activateEnd: Boolean): Calendar {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, day)

        if (activateEnd) {
            calendar.set(Calendar.HOUR_OF_DAY, 23)
            calendar.set(Calendar.MINUTE, 59)
            calendar.set(Calendar.SECOND, 59)
        } else {
            calendar.set(year, month, day, 0, 0, 0)
        }

        return calendar
    }
}