package com.ebt.newsapicleanarchitecture.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ebt.newsapicleanarchitecture.common.DateUtil
import com.ebt.newsapicleanarchitecture.common.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SharedCalendarViewModel @Inject constructor() : ViewModel() {
    private val _calendar = MutableLiveData<Event<String>>()
    val calendar: LiveData<Event<String>>
        get() = _calendar
    var date: String? = null

    fun changeCalendar() {
        if (date == null)
            date = DateUtil.getDateBeforeNowInString(dayAgo = -10)

        _calendar.value = Event(date!!)
    }
}