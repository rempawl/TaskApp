package com.example.taskapp.fragments

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.taskapp.MainActivity

import com.example.taskapp.R
import com.example.taskapp.di.viewModel
import com.example.taskapp.viewmodels.TodayViewModel

class TodayFragment : Fragment() {

    companion object {
        fun newInstance() = TodayFragment()
    }

    private val viewModel: TodayViewModel by viewModel { (activity as MainActivity).appComponent
        .todayViewModel}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.today_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

}
