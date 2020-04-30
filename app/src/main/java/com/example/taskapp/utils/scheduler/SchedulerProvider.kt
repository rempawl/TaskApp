package com.example.taskapp.utils.scheduler

import io.reactivex.Scheduler

interface SchedulerProvider {

    fun getUiScheduler() : Scheduler

    fun getIoScheduler() : Scheduler
}



