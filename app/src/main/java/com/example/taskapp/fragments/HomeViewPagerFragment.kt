package com.example.taskapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.taskapp.MainActivity
import com.example.taskapp.R
import com.example.taskapp.adapters.HomeViewPagerAdapter
import com.example.taskapp.databinding.FragmentHomeViewPagerBinding
import com.google.android.material.tabs.TabLayoutMediator

class HomeViewPagerFragment : Fragment()  {

    lateinit var viewPager : ViewPager2
    lateinit var tabLayoutMediator: TabLayoutMediator

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentHomeViewPagerBinding
            .inflate(inflater,container,false)
        viewPager = binding.viewPager
        viewPager.adapter = HomeViewPagerAdapter(this)

        val tabLayout = binding.tabs
        tabLayoutMediator = TabLayoutMediator(tabLayout,viewPager){tab, position ->
            tab.apply {
                setIcon(getTableIcon(position))
                text = getTableText(position)
            }
        }.apply {  attach() }

        return binding.root
    }
    private fun getTableIcon(position: Int) = when (position) {
        MainActivity.MY_TASKS_FRAGMENT_INDEX -> R.drawable.ic_assignment_black_24dp
        MainActivity.TODAY_FRAGMENT_INDEX -> R.drawable.ic_launcher_foreground
        MainActivity.ADD_TASK_FRAGMENT_INDEX -> R.drawable.ic_add_black_24dp
        else -> throw IndexOutOfBoundsException()
    }
    private fun getTableText(position: Int) = when (position) {
        MainActivity.MY_TASKS_FRAGMENT_INDEX -> getString(R.string.my_tasks)
        MainActivity.TODAY_FRAGMENT_INDEX -> getString(R.string.today)
        MainActivity.ADD_TASK_FRAGMENT_INDEX -> getString(R.string.add_task)
        else -> throw IndexOutOfBoundsException()
    }


}