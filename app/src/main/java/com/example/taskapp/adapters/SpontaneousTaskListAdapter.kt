package com.example.taskapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.taskapp.data.task.Task
import com.example.taskapp.databinding.SpontaneousTaskListItemBinding

typealias TaskID = Long

class SpontaneousTaskListAdapter constructor(
    private val onCheckedChangeListener: (Boolean, TaskID) -> Unit
) :
    ListAdapter<Task, SpontaneousTaskListAdapter.SpontaneousTaskViewHolder>(
        TaskDiffUtilCallback()
    ) {

    inner class SpontaneousTaskViewHolder(private val binding: SpontaneousTaskListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
        }

        fun bind(task: Task) {
            binding.apply {
                this.task = task
                addCheckbox.setOnCheckedChangeListener { _, isChecked ->
                    onCheckedChangeListener(isChecked, binding.task!!.taskID)
                }
                executePendingBindings()
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpontaneousTaskViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding = SpontaneousTaskListItemBinding
            .inflate(inflater, parent, false)

        return SpontaneousTaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SpontaneousTaskViewHolder, position: Int) {
        holder.bind(getItem(position))

    }
}

private class TaskDiffUtilCallback : DiffUtil.ItemCallback<Task>() {
    override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem.taskID == newItem.taskID
    }

}