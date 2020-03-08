package com.example.taskapp.workers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.taskapp.repos.task.TaskRepositoryInterface
import javax.inject.Inject

class UpdateTaskListReceiver @Inject constructor(taskRepository: TaskRepositoryInterface): BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        //todo get tasks from db, iterate and
    }
}
