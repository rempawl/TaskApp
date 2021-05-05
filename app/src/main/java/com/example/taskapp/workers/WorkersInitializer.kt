package com.example.taskapp.workers

import android.content.Context
import androidx.work.Constraints
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkRequest


interface WorkersInitializer {
    fun setUpWorkers(context: Context) {
        val constraints = createConstraints()
        val workRequest = createWorkRequest(constraints)
        enqueueWorkRequest(context = context, workRequest = workRequest)
    }

    fun createConstraints(): Constraints
    fun createWorkRequest(constraints: Constraints): WorkRequest
    fun enqueueWorkRequest(workRequest: WorkRequest, context: Context)
}

