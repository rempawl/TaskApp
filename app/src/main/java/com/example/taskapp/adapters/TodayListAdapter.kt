package com.example.taskapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.taskapp.database.entities.TaskMinimal
import com.example.taskapp.databinding.TaskListItemBinding
import com.example.taskapp.fragments.TodayFragmentDirections
import javax.inject.Inject

class TodayListAdapter @Inject constructor():
    ListAdapter<TaskMinimal, TodayListAdapter.TaskViewHolder>(TaskMinimalDiffCallback()) {

    inner class TaskViewHolder(private val binding: TaskListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(task: TaskMinimal) {
            binding.apply {
                this.task = task
                detailsBtn.setOnClickListener { navigateToTaskDetails(it, task) }
            }
        }

        private fun navigateToTaskDetails(view: View, task: TaskMinimal) {
            Navigation.createNavigateOnClickListener(
                TodayFragmentDirections.navigationTodayToNavigationTaskDetails(task.taskID)
            ).onClick(view)
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


}


class TaskMinimalDiffCallback : DiffUtil.ItemCallback<TaskMinimal>() {
    override fun areItemsTheSame(oldItem: TaskMinimal, newItem: TaskMinimal): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: TaskMinimal, newItem: TaskMinimal): Boolean =
        oldItem.taskID == newItem.taskID
}

