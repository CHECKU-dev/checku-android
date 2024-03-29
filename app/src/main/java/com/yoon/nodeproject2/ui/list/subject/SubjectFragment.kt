package com.yoon.nodeproject2.ui.list.subject

import android.annotation.SuppressLint
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import com.yoon.nodeproject2.R
import com.yoon.nodeproject2.base.BaseFragment
import com.yoon.nodeproject2.databinding.FragmentSubjectBinding
import com.yoon.nodeproject2.ui.MainActivity
import com.yoon.nodeproject2.ui.list.subject.adapter.SubjectAdapter
import com.yoon.nodeproject2.widget.utils.NETWORK_ERROR_MESSAGE
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SubjectFragment : BaseFragment<FragmentSubjectBinding>(R.layout.fragment_subject) {

    val viewModel by viewModels<SubjectViewModel>()
    private lateinit var subjectAdapter: SubjectAdapter

    companion object {
        fun newInstance() = SubjectFragment()
        const val TAG = "SubjectFragment"
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

    @SuppressLint("NotifyDataSetChanged")
    private fun observeRecyclerView() {
        viewModel.subjectList.observe(viewLifecycleOwner) {
            subjectAdapter.submitList(it.toMutableList())
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
            subjectAdapter.notifyItemChanged(it.first, it.second)
        }

    }

    private fun initRecyclerView() {
        subjectAdapter = SubjectAdapter(viewModel)
        binding.rvSubject.adapter = subjectAdapter
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            viewModel.getSubjectData()
        }
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

    fun refresh() {
        viewModel.getSubjectData()
    }

    override fun onPause() {
        super.onPause()
        binding.slidingUpPanelLayout.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
    }

//    override fun onResume() {
//        super.onResume()
//        viewModel.getSubjectData()
//    }

}