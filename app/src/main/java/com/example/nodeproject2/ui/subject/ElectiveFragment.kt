package com.example.nodeproject2.ui.subject

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.nodeproject2.base.BaseFragment
import com.example.nodeproject2.databinding.FragmentElectiveBinding
import com.example.nodeproject2.ui.MainActivity
import com.example.nodeproject2.ui.subject.adapter.ElectiveAdapter
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ElectiveFragment : BaseFragment<FragmentElectiveBinding>(com.example.nodeproject2.R.layout.fragment_elective) {

    private val viewModel by viewModels<ElectiveViewModel>()
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
            hideLoadingDialog()
        }

        viewModel.subjectErrorToastEvent.observe(viewLifecycleOwner) {
            showCustomToast("실패 실패 실패 실패")
            hideLoadingDialog()
        }

        viewModel.subjectWaitEvent.observe(viewLifecycleOwner) {
            showLoadingDialog()
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
        if(hidden) {
        }else {
            viewModel.getElectives()
        }
    }

}