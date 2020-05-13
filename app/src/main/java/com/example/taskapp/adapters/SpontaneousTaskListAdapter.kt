package com.example.taskapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.taskapp.database.entities.task.DefaultTask
import com.example.taskapp.databinding.SpontaneousTaskListItemBinding
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject

typealias TaskID = Long

class SpontaneousTaskListAdapter @AssistedInject constructor(
    @Assisted private val onCheckedChangeListener: (Boolean, TaskID) -> Unit
) :
    ListAdapter<DefaultTask, SpontaneousTaskListAdapter.SpontaneousTaskViewHolder>(
        TaskDiffUtilCallback()
    ) {
    @AssistedInject.Factory
    interface Factory{
        fun create(onCheckedChangeListener: (Boolean, Long) -> Unit) : SpontaneousTaskListAdapter
    }


    inner class SpontaneousTaskViewHolder(private val binding: SpontaneousTaskListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
       }

        fun bind(task: DefaultTask) {
            binding.apply {
                this.task = task
                executePendingBindings()
                addCheckbox.setOnCheckedChangeListener { _, isChecked ->
                    onCheckedChangeListener(isChecked, binding.task!!.taskID)
                }

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

private class TaskDiffUtilCallback : DiffUtil.ItemCallback<DefaultTask>() {
    override fun areItemsTheSame(oldItem: DefaultTask, newItem: DefaultTask): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: DefaultTask, newItem: DefaultTask): Boolean {
        return oldItem.taskID == newItem.taskID
    }

}