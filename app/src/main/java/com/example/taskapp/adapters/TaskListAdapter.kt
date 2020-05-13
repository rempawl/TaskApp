package com.example.taskapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.taskapp.database.entities.task.TaskMinimal
import com.example.taskapp.databinding.TaskListItemBinding
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject


class TaskListAdapter @AssistedInject constructor(@Assisted private val onItemClickListener: (TaskMinimal) -> Unit ) :
    ListAdapter<TaskMinimal, TaskListAdapter.TaskViewHolder>(TaskMinimalDiffCallback()) {



    @AssistedInject.Factory
    interface Factory {
        fun create(onItemClickListener: (TaskMinimal) -> Unit): TaskListAdapter
    }


    inner class TaskViewHolder(private val binding: TaskListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(task: TaskMinimal) {
            binding.apply {
                this.task = task
                taskCard.setOnClickListener { onItemClickListener(task) }
                executePendingBindings()
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(
            TaskListItemBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
         const val PORTRAIT_COLUMN_COUNT = 2
         const val LANDSCAPE_COLUMN_COUNT = 4
    }
}


class TaskMinimalDiffCallback : DiffUtil.ItemCallback<TaskMinimal>() {
    override fun areItemsTheSame(oldItem: TaskMinimal, newItem: TaskMinimal): Boolean =
        oldItem === newItem

    override fun areContentsTheSame(oldItem: TaskMinimal, newItem: TaskMinimal): Boolean =
        oldItem.taskID == newItem.taskID
}
