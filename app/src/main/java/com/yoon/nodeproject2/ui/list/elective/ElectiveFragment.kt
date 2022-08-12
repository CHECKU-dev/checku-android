package com.yoon.nodeproject2.ui.list.elective

import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import com.yoon.nodeproject2.R
import com.yoon.nodeproject2.base.BaseFragment
import com.yoon.nodeproject2.databinding.FragmentElectiveBinding
import com.yoon.nodeproject2.ui.MainActivity
import com.yoon.nodeproject2.ui.list.elective.adapter.ElectiveAdapter
import com.yoon.nodeproject2.widget.utils.NETWORK_ERROR_MESSAGE
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ElectiveFragment : BaseFragment<FragmentElectiveBinding>(R.layout.fragment_elective) {

    val viewModel by viewModels<ElectiveViewModel>()
    private lateinit var electiveAdapter: ElectiveAdapter

    companion object {
        fun newInstance() = ElectiveFragment()
        const val TAG = "ElectiveFragment"
    }

    override fun doViewCreated() {
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        initRecyclerView()
        observeRecyclerView()

        binding.searchLayout.setOnClickListener {
            (activity as MainActivity).changeToSearch(TAG)
        }

        initSlidingPanel()


    }

    private fun observeRecyclerView() {
        viewModel.subjectList.observe(viewLifecycleOwner) {
            electiveAdapter.submitList(it)
            if (it.size == 0) {
                binding.emptyView.visibility = View.VISIBLE
            } else {
                binding.emptyView.visibility = View.GONE
            }
            hideLoadingDialog()
        }

        viewModel.subjectErrorToastEvent.observe(viewLifecycleOwner) {
            showCustomToast(NETWORK_ERROR_MESSAGE)
            hideLoadingDialog()
        }

        viewModel.subjectWaitEvent.observe(viewLifecycleOwner) {
            showLoadingDialog()
        }

        viewModel.updateRecyclerViewItemEvent.observe(viewLifecycleOwner) {
            electiveAdapter.notifyItemChanged(it.first, it.second)
        }

    }

    private fun initRecyclerView() {
        electiveAdapter = ElectiveAdapter(viewModel)
        binding.rvSubject.adapter = electiveAdapter
    }

    private fun initSlidingPanel() {
        binding.apply {
            slidingUpPanelLayout.setFadeOnClickListener {
                slidingUpPanelLayout.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
            }

            rvSubject.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (slidingUpPanelLayout.panelState == SlidingUpPanelLayout.PanelState.EXPANDED) {
                        slidingUpPanelLayout.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
                    }
                }
            })

        }
    }


    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            viewModel.getElectives()
        }
    }

    fun refresh() {
        viewModel.getElectives()
    }

    override fun onPause() {
        super.onPause()
        binding.slidingUpPanelLayout.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
    }

//    override fun onResume() {
//        super.onResume()
//        viewModel.getElectives()
//    }

}