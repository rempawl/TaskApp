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
import com.example.taskapp.databinding.EditTaskFragmentBinding
import com.example.taskapp.di.viewModel
import com.example.taskapp.utils.ReminderDialogFragmentsDisplayer.showBegDatePickerDialog
import com.example.taskapp.utils.ReminderDialogFragmentsDisplayer.showDaysOfWeekPickerDialog
import com.example.taskapp.utils.ReminderDialogFragmentsDisplayer.showDurationDaysPickerDialog
import com.example.taskapp.utils.ReminderDialogFragmentsDisplayer.showEndDatePickerDialog
import com.example.taskapp.utils.ReminderDialogFragmentsDisplayer.showFrequencyPickerDialog
import com.example.taskapp.utils.ReminderDialogFragmentsDisplayer.showNotificationPickerDialog
import com.example.taskapp.utils.VisibilityChanger
import com.example.taskapp.viewmodels.EditTaskViewModel
import com.google.android.material.radiobutton.MaterialRadioButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditTaskFragment : Fragment() {

    companion object {
        fun newInstance() = EditTaskFragment()
    }

    private val viewModel: EditTaskViewModel by viewModel {
        (activity as MainActivity).appComponent.editTaskViewModelFactory.create(args.task)
    }

    private val args: EditTaskFragmentArgs by navArgs()

    private var binding: EditTaskFragmentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = EditTaskFragmentBinding.inflate(inflater, container, false)

        viewModel.toastText.observe(viewLifecycleOwner, Observer { id ->
            if (id != null) {
                Toast.makeText(context, getString(id), Toast.LENGTH_SHORT).show()
            }
        })

        return binding!!.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setUpBinding()
//        val toolbar = (activity as MainActivity).findViewById<Toolbar>(R.id.toolbar)
//        .apply{
//            setNavigationIcon(R.drawable.ic_close_black_24dp)
//            setNavigationOnClickListener {  } //todo show confirm dialog
//        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding?.apply {
            viewModel = null
            lifecycleOwner = null
        }
        binding = null
    }

    private fun setUpBinding() {
        binding?.apply {
            viewModel = this@EditTaskFragment.viewModel
            lifecycleOwner = viewLifecycleOwner
            confirmButton.setOnClickListener { editTask() }
            setTimeOfNotification.setOnClickListener {
                showNotificationPickerDialog(
                    this@EditTaskFragment.viewModel.notificationModel,
                    childFragmentManager
                )
            }
        }
        setUpDurationLayout()
        setupFrequencyLayout()
    }

    private fun editTask() {
        CoroutineScope(Dispatchers.Main).launch { viewModel.saveEditedTask() }
        findNavController().navigate(
            EditTaskFragmentDirections.navigationEditTaskToNavigationMyTasks()
        )
    }

    fun setUpDurationLayout() {
        binding?.apply {
            beginningDateBtn.setOnClickListener {
                showBegDatePickerDialog(
                    this@EditTaskFragment.viewModel.durationModel,
                    childFragmentManager
                )
            }
            setDurationDaysBtn.setOnClickListener {
                showDurationDaysPickerDialog(
                    this@EditTaskFragment.viewModel.durationModel,
                    childFragmentManager
                )
            }
            setEndDateBtn.setOnClickListener {
                showEndDatePickerDialog(
                    this@EditTaskFragment.viewModel.durationModel,
                    childFragmentManager
                )
            }
            durationRadioGroup.apply {
                setDurationButtonsVisibility(checkedRadioButtonId) //to show proper one on rotation
                setOnCheckedChangeListener { _, id ->
                    onDurationRadioChecked(id)
                }
            }
        }
    }

    private fun onDurationRadioChecked(id: Int) {
        val durationModel = viewModel.durationModel
        binding?.run {
            when (id) {
                xDaysDurationRadio.id -> {
                    durationModel.setDaysDurationState()
                }
                endDateRadio.id -> {
                    durationModel.setEndDateDurationState()
                }
                noEndDateRadio.id -> {
                    durationModel.setNoEndDateDurationState()
                }
                else -> throw NoSuchElementException("There is no matching button")
            }
            setDurationButtonsVisibility(id)
        }
    }


    private fun setupFrequencyLayout() {
        binding?.apply {
            frequencyRadioGroup.apply {
                setFrequencyButtonsVisibility(checkedRadioButtonId) //on rotation
                setOnCheckedChangeListener { _, id ->
                    onFrequencyRadioCheck(id)
                    setFrequencyButtonsVisibility(id)
                }
            }
            setDailyFrequencyBtn.setOnClickListener {
                showFrequencyPickerDialog(
                    this@EditTaskFragment.viewModel.frequencyModel,
                    childFragmentManager
                )
            }
            setDaysOfWeekBtn.setOnClickListener {
                showDaysOfWeekPickerDialog(
                    childFragmentManager = childFragmentManager,
                    frequencyModel = this@EditTaskFragment.viewModel.frequencyModel
                )
            }
        }
    }

    private fun onFrequencyRadioCheck(id: Int) {
        val frequencyModel = viewModel.frequencyModel
        binding?.run {
            when (activity?.findViewById<MaterialRadioButton>(id)) {
                dailyFreqRadio -> {
                    frequencyModel.setDailyFrequency()
                }
                daysOfWeekRadio -> {
                    frequencyModel.setDaysOfWeekFrequency()
                }

                else -> throw NoSuchElementException("There is no matching button")
            }
        }
        setFrequencyButtonsVisibility(id)

    }


    private fun setDurationButtonsVisibility(id: Int) {
        binding?.run {
            val allBtns = listOf(
                setDurationDaysBtn,
                setEndDateBtn
            )
            when (id) {
                xDaysDurationRadio.id -> VisibilityChanger.changeViewsHelper(
                    listOf(setDurationDaysBtn),
                    allBtns
                )
                endDateRadio.id -> VisibilityChanger.changeViewsHelper(
                    listOf(setEndDateBtn),
                    allBtns
                )
                noEndDateRadio.id -> VisibilityChanger.changeViewsHelper(
                    null,
                    allBtns
                )
                else -> throw NoSuchElementException("There is no matching button")
            }
        }
    }

    private fun setFrequencyButtonsVisibility(id: Int) {
        binding?.run {
            val allBtns = listOf(
                setDailyFrequencyBtn,
                setDaysOfWeekBtn
            )

            when (id) {
                dailyFreqRadio.id -> VisibilityChanger.changeViewsHelper(
                    listOf(setDailyFrequencyBtn),
                    allBtns
                )
                daysOfWeekRadio.id -> VisibilityChanger.changeViewsHelper(
                    listOf(setDaysOfWeekBtn),
                    allBtns
                )
                else -> throw NoSuchElementException("There is no matching button")
            }
        }
    }

}
