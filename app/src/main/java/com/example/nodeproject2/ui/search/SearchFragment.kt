package com.example.nodeproject2.ui.search

import android.annotation.SuppressLint
import android.content.Context
import android.view.KeyEvent
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.viewModels
import com.example.nodeproject2.R
import com.example.nodeproject2.base.BaseFragment
import com.example.nodeproject2.databinding.FragmentSearchBinding
import com.example.nodeproject2.ui.MainActivity
import com.example.nodeproject2.ui.search.adapter.SearchAdapter
import com.example.nodeproject2.widget.utils.NETWORK_ERROR_MESSAGE
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_search.*

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(R.layout.fragment_search),
    MainActivity.OnBackPressedListener {

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
                if (binding.etSearch.text.isNullOrEmpty()) {
                    showCustomToast("검색어를 입력해주세요.")
                    return@setOnKeyListener true
                }
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

    @SuppressLint("SetTextI18n")
    private fun observeRecyclerView() {
        viewModel.subjectList.observe(viewLifecycleOwner) {
            binding.tvSearchResult.text = "\'" + viewModel.searchQuery + "\'" + "(으)로 검색 결과"
            searchAdapter.submitList(it.toMutableList()) {
                if (viewModel.paging.page.value == 1) {
                    binding.rvSearch.scrollToPosition(0)
                }
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
            searchAdapter.notifyItemChanged(it.first, it.second)
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
            activity.currentFocus?.let {
                imm.hideSoftInputFromWindow(it.windowToken, 0)
            }
        }
    }

    override fun onBackPressed() {
        (activity as MainActivity).changeToSubject()
    }

}