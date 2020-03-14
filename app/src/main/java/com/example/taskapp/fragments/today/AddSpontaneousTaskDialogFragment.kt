package com.example.taskapp.fragments.today

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taskapp.MainActivity
import com.example.taskapp.adapters.SpontaneousTaskListAdapter
import com.example.taskapp.databinding.AddSpontaneousTasksFragmentBinding
import com.example.taskapp.viewmodels.AddSpontaneousTasksViewModel
import javax.inject.Inject

class AddSpontaneousTaskDialogFragment
    : DialogFragment() {
    @Inject
    lateinit var viewModel: AddSpontaneousTasksViewModel

    @Inject
    lateinit var spontaneousTaskListAdapter: SpontaneousTaskListAdapter

    private var binding: AddSpontaneousTasksFragmentBinding? = null

    init {
        Log.d(MainActivity.TAG, "new one")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).appComponent.inject(this)
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
        binding = AddSpontaneousTasksFragmentBinding.inflate(inflater, container, false)
        setupLayout(binding!!)
        return binding!!.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun setupLayout(binding: AddSpontaneousTasksFragmentBinding) {
        binding.apply {
            confirmButton.setOnClickListener { addSpontaneousTasks() }
            cancelButton.setOnClickListener { dismiss() }

            taskList.apply {
                adapter = spontaneousTaskListAdapter
//                val spanCount =
//                    if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
//                        PORTRAIT_COLUMN_COUNT
//                    } else {
//                        LANDSCAPE_COLUMN_COUNT
//                    }
                layoutManager = LinearLayoutManager(requireContext())
//                    GridLayoutManager(context, spanCount)
                setHasFixedSize(true)
            }
        }
        viewModel.tasks.observe(viewLifecycleOwner, Observer { tasks ->
            spontaneousTaskListAdapter.submitList(tasks)
        })


    }

    private fun addSpontaneousTasks() {

        viewModel.addSpontaneousTasks(spontaneousTaskListAdapter.checkedTasksIds)
        spontaneousTaskListAdapter.checkedTasksIds.forEach {
            Log.d(MainActivity.TAG, it.toString())

        }
        //add to db
        dismiss()
    }

    companion object {
        const val PORTRAIT_COLUMN_COUNT = 2
        const val LANDSCAPE_COLUMN_COUNT = 4

    }
}