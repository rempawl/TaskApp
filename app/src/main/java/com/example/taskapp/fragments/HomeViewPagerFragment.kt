package com.example.taskapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.example.taskapp.MainActivity
import com.example.taskapp.R
import com.example.taskapp.adapters.HomeViewPagerAdapter
import com.example.taskapp.databinding.FragmentHomeViewPagerBinding
import com.google.android.material.tabs.TabLayoutMediator

class HomeViewPagerFragment : Fragment() {

    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayoutMediator: TabLayoutMediator
    private val args: HomeViewPagerFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentHomeViewPagerBinding
            .inflate(inflater, container, false)
        viewPager = binding.viewPager
        viewPager.adapter = HomeViewPagerAdapter(this)

        val tabLayout = binding.tabs
        this.tabLayoutMediator = TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.apply {
                setIcon(getTableIcon(position))
                text = getTableText(position)
            }
        }.apply { attach() }

        if (args.wasTaskAdded) {
            viewPager.currentItem = MainActivity.MY_TASKS_FRAGMENT_INDEX
        }
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        tabLayoutMediator.detach()
        if (viewPager.adapter != null) {
            viewPager.adapter == null
        }

    }

    private fun getTableIcon(position: Int) = when (position) {
        MainActivity.MY_TASKS_FRAGMENT_INDEX -> R.drawable.ic_assignment_black_24dp
        MainActivity.TODAY_FRAGMENT_INDEX -> R.drawable.ic_launcher_foreground
        else -> throw IndexOutOfBoundsException()
    }

    private fun getTableText(position: Int) = when (position) {
        MainActivity.MY_TASKS_FRAGMENT_INDEX -> getString(R.string.my_tasks)
        MainActivity.TODAY_FRAGMENT_INDEX -> getString(R.string.today)
        else -> throw IndexOutOfBoundsException()
    }


}