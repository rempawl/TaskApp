package com.example.taskapp.utils

import com.example.taskapp.utils.providers.SchedulerProvider
import io.reactivex.Scheduler
import io.reactivex.schedulers.TestScheduler

class TestSchedulerProvider :
    SchedulerProvider {
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