package com.example.taskapp.utils.scheduler

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DefaultSchedulerProvider @Inject constructor():
    SchedulerProvider {

    override fun getUiScheduler(): Scheduler {
        return AndroidSchedulers.mainThread()
    }

    override fun getIoScheduler(): Scheduler {
        return Schedulers.io()
    }

}
