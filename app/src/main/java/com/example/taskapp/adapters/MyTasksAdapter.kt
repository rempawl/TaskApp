package com.example.taskapp.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.taskapp.database.entities.Task

class MyTasksAdapter : ListAdapter<Task, MyTasksAdapter.TaskViewHolder>(DiffCallBack()) {

    class TaskViewHolder(val binding: View) : RecyclerView.ViewHolder(binding) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        TODO()
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}


private class DiffCallBack : DiffUtil.ItemCallback<Task>() {
    override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
