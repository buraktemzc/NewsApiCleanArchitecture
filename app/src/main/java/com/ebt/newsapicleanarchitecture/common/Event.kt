package com.ebt.newsapicleanarchitecture.common

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

open class Event<out T>(private val content: T) {
    var hasBeenHandled = false
        private set

    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    fun peekContent(): T = content
}

fun <T> LiveData<out Event<T>>.observeEvent(owner: LifecycleOwner, callback: (T) -> Unit) {
    observe(owner, Observer { it?.getContentIfNotHandled()?.let(callback) })
}