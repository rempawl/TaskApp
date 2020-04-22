package com.example.taskapp.utils

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.TestScheduler
import javax.inject.Inject

interface SchedulerProvider {

    fun getUiScheduler() : Scheduler

    fun getIoScheduler() : Scheduler
}


class DefaultSchedulerProvider @Inject constructor(): SchedulerProvider{

    override fun getUiScheduler(): Scheduler {
        return AndroidSchedulers.mainThread()
    }

    override fun getIoScheduler(): Scheduler {
        return Schedulers.io()
    }

}

class TestSchedulerProvider : SchedulerProvider{
    override fun getUiScheduler(): Scheduler {
        return TEST_SCHEDULER
    }

    override fun getIoScheduler(): Scheduler {
        return TEST_SCHEDULER
    }

    companion object{
        val TEST_SCHEDULER = TestScheduler()
    }
}