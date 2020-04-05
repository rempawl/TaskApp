package com.example.taskapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.taskapp.database.entities.DefaultTask
import com.example.taskapp.databinding.SpontaneousTaskListItemBinding
import javax.inject.Inject

typealias taskID = Long

class SpontaneousTaskListAdapter @Inject constructor() :
    ListAdapter<DefaultTask, SpontaneousTaskListAdapter.SpontaneousTaskViewHolder>(TaskDiffUtilCallback()) {


    private val _checkedTasksIds = mutableSetOf<taskID>()
    val checkedTasksIds : List<taskID>
    get() = _checkedTasksIds.toList()

    inner class SpontaneousTaskViewHolder(private val binding: SpontaneousTaskListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.addCheckbox.setOnCheckedChangeListener { _, isChecked ->
                onAddCheckboxChecked(isChecked)
            }
        }

        fun bind(task: DefaultTask) {
            binding.task = task
        }

        private fun onAddCheckboxChecked(isChecked: Boolean) {
            val id = binding.task?.taskID ?: return
            if (isChecked) {
                _checkedTasksIds.add(id)
            } else {
                _checkedTasksIds.remove(id)
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