package com.example.nodeproject2.ui.list.list

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.nodeproject2.ui.list.elective.ElectiveFragment
import com.example.nodeproject2.ui.list.subject.SubjectFragment


private const val NUM_TABS = 2

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return NUM_TABS
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return SubjectFragment()
            1 -> return ElectiveFragment()
        }
        return SubjectFragment()
    }

}