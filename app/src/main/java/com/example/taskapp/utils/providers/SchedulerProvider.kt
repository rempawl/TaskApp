package com.example.taskapp.utils.providers

import io.reactivex.Scheduler

interface SchedulerProvider {

    fun getUiScheduler() : Scheduler

    fun getIoScheduler() : Scheduler
}



