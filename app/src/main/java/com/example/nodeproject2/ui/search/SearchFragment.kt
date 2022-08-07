package com.example.nodeproject2.ui.search

import android.content.Context
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.viewModels
import com.example.nodeproject2.R
import com.example.nodeproject2.base.BaseFragment
import com.example.nodeproject2.databinding.FragmentSearchBinding
import com.example.nodeproject2.ui.MainActivity
import com.example.nodeproject2.ui.search.adapter.SearchAdapter
import com.example.nodeproject2.ui.subject.SubjectFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(R.layout.fragment_search) {

    private lateinit var searchAdapter: SearchAdapter
    private val viewModel by viewModels<SearchViewModel>()

    companion object {
        fun newInstance() = SearchFragment()
        const val TAG = "SearchFragment"
    }

    override fun doViewCreated() {

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.etSearch.setText("")
        binding.etSearch.setOnKeyListener { _, keyCode, event ->
            if ((event.action == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                downKeyboard()
                viewModel.changeSubject()
                return@setOnKeyListener true
            } else {
                return@setOnKeyListener false
            }
        }
        upKeyboard()
        binding.backButton.setOnClickListener {
            downKeyboard()
            (activity as MainActivity).changeToSubject()
        }

        initRecyclerView()
        observeRecyclerView()
    }

    private fun initRecyclerView() {
        searchAdapter = SearchAdapter(viewModel)
        binding.rvSearch.adapter = searchAdapter

    }

    private fun observeRecyclerView() {
        viewModel.subjectList.observe(viewLifecycleOwner) {
            binding.tvSearchResult.setText("\'" + viewModel.searchQuery + "\'" + "(으)로 검색 결과")
            searchAdapter.submitList(it.toMutableList()) {
                if (viewModel.paging.page.value == 1) {
                    binding.rvSearch.scrollToPosition(0)
                }
            }
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

    private fun upKeyboard() {
        activity?.also { activity ->
            val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
            binding.etSearch.requestFocus()
        }
    }

    private fun downKeyboard() {
        activity?.also { activity ->
            val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(activity.currentFocus!!.windowToken, 0)
        }
    }


}