package com.example.taskapp.fragments.today

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taskapp.MainActivity
import com.example.taskapp.adapters.SpontaneousTaskListAdapter
import com.example.taskapp.databinding.AddSpontaneousTasksFragmentBinding
import com.example.taskapp.di.viewModel
import com.example.taskapp.viewmodels.AddSpontaneousTasksViewModel
import javax.inject.Inject

class AddSpontaneousTaskDialogFragment : DialogFragment() {

    //todo onRotation check
    private val viewModel: AddSpontaneousTasksViewModel by viewModel {
        injectViewModel()
    }

    private val appComponent by lazy {

        (activity as MainActivity).appComponent
    }


    @Inject
    lateinit var spontaneousTaskListAdapterFactory: SpontaneousTaskListAdapter.Factory

    private val spontaneousTaskListAdapter: SpontaneousTaskListAdapter by lazy {
        spontaneousTaskListAdapterFactory.create(viewModel.onCheckedListener)
    }

    private var binding: AddSpontaneousTasksFragmentBinding? = null


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
        binding = AddSpontaneousTasksFragmentBinding.inflate(inflater, container, false)
        setupLayout(binding!!)
        return binding!!.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding!!.taskList.adapter = null
        binding = null
    }

    private fun setupLayout(binding: AddSpontaneousTasksFragmentBinding) {
        binding.apply {
            confirmButton.setOnClickListener { addSpontaneousTasks() }

            cancelButton.setOnClickListener { dismiss() }
            toolbar.setNavigationOnClickListener { dismiss() }

            taskList.apply {
                adapter = spontaneousTaskListAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }
        }
        viewModel.tasks.observe(viewLifecycleOwner, Observer { tasks ->
            spontaneousTaskListAdapter.submitList(tasks)
        })


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

    companion object {
        const val PORTRAIT_COLUMN_COUNT = 2
        const val LANDSCAPE_COLUMN_COUNT = 4

    }
}