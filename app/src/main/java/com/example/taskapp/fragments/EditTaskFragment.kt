package com.example.taskapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.taskapp.MainActivity
import com.example.taskapp.databinding.AddEditTaskFragmentBinding
import com.example.taskapp.di.viewModel
import com.example.taskapp.utils.bindingArranger.EditTaskBindingArranger
import com.example.taskapp.viewmodels.reminder.ReminderViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class EditTaskFragment : Fragment() {

    companion object {
        fun newInstance() = EditTaskFragment()
    }

    private val viewModel: ReminderViewModel by viewModel {
        (activity as MainActivity).appComponent.editTaskViewModelFactory.create(args.task)
    }

    private val args: EditTaskFragmentArgs by navArgs()

    private var binding: AddEditTaskFragmentBinding? = null

    private var bindingOrganiser: EditTaskBindingArranger? = null

    private val editTask: View.OnClickListener = View.OnClickListener {
        CoroutineScope(Dispatchers.Main).launch { viewModel.saveTask() }

        findNavController().navigate(
            EditTaskFragmentDirections.navigationEditTaskToNavigationMyTasks()
        )
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AddEditTaskFragmentBinding.inflate(inflater, container, false)

        bindingOrganiser = EditTaskBindingArranger(binding!!, this, viewModel, editTask)

        setUpObservers()

        return binding!!.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
        bindingOrganiser = null
    }


    private fun setUpObservers() {
        viewModel.toastText.observe(viewLifecycleOwner, Observer { id ->
            if (id != null) {
                Toast.makeText(context, getString(id), Toast.LENGTH_SHORT).show()
            }
        })
    }

}
