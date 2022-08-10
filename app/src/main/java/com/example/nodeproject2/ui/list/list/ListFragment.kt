package com.example.nodeproject2.ui.list.list

import android.util.Log
import androidx.fragment.app.viewModels
import com.example.nodeproject2.R
import com.example.nodeproject2.base.BaseFragment
import com.example.nodeproject2.databinding.FragmentListBinding
import com.example.nodeproject2.ui.list.elective.ElectiveFragment
import com.example.nodeproject2.ui.list.elective.ElectiveViewModel
import com.example.nodeproject2.ui.list.subject.SubjectFragment
import com.example.nodeproject2.ui.list.subject.SubjectViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_list.*


@AndroidEntryPoint
class ListFragment : BaseFragment<FragmentListBinding>(R.layout.fragment_list) {

    private var currentTab = "SubjectFragment"

    private val tabTitleArray = arrayOf(
        "전공",
        "교양",
    )


    companion object {
        fun newInstance() = ListFragment()
        const val TAG = "ListFragment"
    }

    override fun doViewCreated() {
        initTab()
    }

    private fun initTab() {
        binding.apply {
            viewPager.adapter = ViewPagerAdapter(requireActivity().supportFragmentManager, lifecycle)

            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = tabTitleArray[position]
            }.attach()

            viewPager.isUserInputEnabled = false
        }
    }

    fun changeTab() {
        binding.apply {
            if (viewPager.currentItem == 0) {
                currentTab = "ElectiveFragment"
                viewPager.setCurrentItem(1, true)
            } else {
                currentTab = "SubjectFragment"
                viewPager.setCurrentItem(0, true)
            }
        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            if (currentTab.equals("SubjectFragment")) {
                val fragment = requireActivity().supportFragmentManager.findFragmentByTag("f0") as SubjectFragment
                fragment.refresh()
            } else {
                val fragment = requireActivity().supportFragmentManager.findFragmentByTag("f1") as ElectiveFragment
                fragment.refresh()
            }
        }
    }

}