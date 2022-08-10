package com.example.nodeproject2.ui.list.list

import com.example.nodeproject2.R
import com.example.nodeproject2.base.BaseFragment
import com.example.nodeproject2.databinding.FragmentListBinding
import com.example.nodeproject2.ui.list.elective.ElectiveFragment
import com.example.nodeproject2.ui.list.subject.SubjectFragment
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ListFragment : BaseFragment<FragmentListBinding>(R.layout.fragment_list) {

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
                viewPager.setCurrentItem(1, true)
            } else {
                viewPager.setCurrentItem(0, true)
            }
        }
    }



}