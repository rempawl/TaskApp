package com.example.taskapp.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.taskapp.MainActivity
import com.example.taskapp.fragments.AddTaskFragment
import com.example.taskapp.fragments.MyTasksFragment
import com.example.taskapp.fragments.TodayFragment

class HomeViewPagerAdapter(fragment : Fragment) : FragmentStateAdapter(fragment)  {

    private val fragmentTab: Map<Int,() -> Fragment> = mapOf(
        MainActivity.TODAY_FRAGMENT_INDEX to { TodayFragment() },
        MainActivity.MY_TASKS_FRAGMENT_INDEX to { MyTasksFragment() }
    )
    override fun getItemCount(): Int  = fragmentTab.size

    override fun createFragment(position: Int): Fragment {
        return fragmentTab[position]?.invoke() ?: throw IndexOutOfBoundsException()
    }

}