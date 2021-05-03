package com.ebt.newsapicleanarchitecture.presentation.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ebt.newsapicleanarchitecture.data.model.APIResponse
import com.ebt.newsapicleanarchitecture.data.util.Result
import com.ebt.newsapicleanarchitecture.domain.usecase.GetArticlesUseCase
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
    val apiResult: MutableLiveData<Result<APIResponse>> = MutableLiveData()

    fun getArticles() = viewModelScope.launch(Dispatchers.IO) {
        apiResult.postValue(Result.Loading())

        try {
            if (isNetworkAvailable(app)) {
                val result = getArticlesUseCase.execute(1, "publishedAt", "football", null, null)
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