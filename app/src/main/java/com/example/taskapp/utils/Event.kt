package com.example.taskapp.utils

import androidx.lifecycle.Observer

class Event<T>( private val content : T) {
    private var wasHandled : Boolean = false

    fun getContentIfNotHandled() : T?{
        if(wasHandled)
            return null

        wasHandled = true
        return content
    }
}

class EventObserver<T>(private val handler : (T) -> Unit) : Observer<Event<T>> {

    override fun onChanged(event: Event<T>?) {
        event?.getContentIfNotHandled()?.let{
            handler(it)
        }
    }
}