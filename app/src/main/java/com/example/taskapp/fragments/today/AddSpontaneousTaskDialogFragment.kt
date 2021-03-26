package com.example.taskapp.fragments.today

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taskapp.MainActivity
import com.example.taskapp.adapters.SpontaneousTaskListAdapter
import com.example.taskapp.data.Result
import com.example.taskapp.data.task.Task
import com.example.taskapp.databinding.AddSpontaneousTasksFragmentBinding
import com.example.taskapp.di.viewModel
import com.example.taskapp.utils.autoCleared
import com.example.taskapp.viewmodels.AddSpontaneousTasksViewModel

class AddSpontaneousTaskDialogFragment : DialogFragment() {

    //todo onRotation check
    private val viewModel: AddSpontaneousTasksViewModel by viewModel {
        injectViewModel()
    }

    private val appComponent by lazy {
        (activity as MainActivity).appComponent
    }


    private var spontaneousTaskListAdapter: SpontaneousTaskListAdapter by autoCleared()

    private var binding: AddSpontaneousTasksFragmentBinding by autoCleared()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        injectMembers()
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        retainInstance = true
        return super.onCreateDialog(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        spontaneousTaskListAdapter = SpontaneousTaskListAdapter(viewModel.onCheckedListener)

        binding = AddSpontaneousTasksFragmentBinding.inflate(inflater, container, false)
        setupLayout(binding)
        return binding.root
    }


    private fun setupLayout(binding: AddSpontaneousTasksFragmentBinding) {
        binding.apply {
            confirmButton.setOnClickListener { addSpontaneousTasks() }
            cancelButton.setOnClickListener { dismiss() }
            toolbar.setNavigationOnClickListener { dismiss() }

            taskList.apply {
                adapter = spontaneousTaskListAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(false)
            }
        }
        viewModel.result.observe(viewLifecycleOwner, { res ->
            res.takeIf { it.checkIfIsSuccessAndListOf<Task>() }?.let {
                submitResult(it as Result.Success)
            }
        })
    }

    private fun submitResult(result: Result.Success<*>) {
        @Suppress("UNCHECKED_CAST")
        spontaneousTaskListAdapter.submitList(result.data as List<Task>)
    }

    private fun addSpontaneousTasks() {
        viewModel.addSpontaneousTasks()
        dismiss()
    }

    private fun injectMembers() {
        appComponent.inject(this)
    }

    private fun injectViewModel() =
        appComponent.addSpontaneousTasksViewModel

}