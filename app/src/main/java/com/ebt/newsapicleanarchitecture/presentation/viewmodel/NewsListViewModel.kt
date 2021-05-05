package com.ebt.newsapicleanarchitecture.presentation.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ebt.newsapicleanarchitecture.common.DateUtil
import com.ebt.newsapicleanarchitecture.common.Event
import com.ebt.newsapicleanarchitecture.data.model.APIResponse
import com.ebt.newsapicleanarchitecture.data.util.Result
import com.ebt.newsapicleanarchitecture.domain.usecase.GetArticlesUseCase
import com.ebt.newsapicleanarchitecture.presentation.dialog.CalendarDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class NewsListViewModel @Inject constructor(
    private val app: Application,
    private val getArticlesUseCase: GetArticlesUseCase
) : ViewModel() {
    private val _defaultDateInitialized = MutableLiveData<Event<CalendarDate>>()
    val defaultDateInitialized: LiveData<Event<CalendarDate>>
        get() = _defaultDateInitialized
    val apiResult: MutableLiveData<Result<APIResponse>> = MutableLiveData()


    init {
        val defaultFromDate = DateUtil.getDateBeforeNow(-10)
        val defaultToDate = DateUtil.getDateBeforeNow(0)
        getArticles(
            DateUtil.getDateInString(
                dateFormat = DateUtil.API_DATE_FORMAT,
                calendar = defaultFromDate
            ),
            DateUtil.getDateInString(
                dateFormat = DateUtil.API_DATE_FORMAT,
                calendar = defaultToDate
            )
        )
        _defaultDateInitialized.value = Event(CalendarDate(defaultFromDate, defaultToDate))
    }

    fun getArticles(fromDate: String, toDate: String) = viewModelScope.launch(Dispatchers.IO) {
        apiResult.postValue(Result.Loading())

        try {
            if (isNetworkAvailable(app)) {
                val result =
                    getArticlesUseCase.execute(null, "publishedAt", "football", fromDate, toDate)
                withContext(Dispatchers.Main) {
                    apiResult.value = result
                }
            } else {
                apiResult.value = Result.Error("No internet connection")
            }
        } catch (e: Exception) {
            apiResult.value = Result.Error(e.message.toString())
        }
    }

    private fun isNetworkAvailable(context: Context?): Boolean {
        if (context == null) return false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }
                }
            }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true
            }
        }
        return false
    }
}