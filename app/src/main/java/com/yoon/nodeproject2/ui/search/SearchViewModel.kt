package com.yoon.nodeproject2.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.onFailure
import com.skydoves.sandwich.onSuccess
import com.yoon.nodeproject2.data.model.AddOrRemoveSubjectRequest
import com.yoon.nodeproject2.data.model.Subject
import com.yoon.nodeproject2.di.CheckuApplication
import com.yoon.nodeproject2.repository.SearchRepository
import com.yoon.nodeproject2.widget.utils.MutableSingleLiveData
import com.yoon.nodeproject2.widget.utils.Paging
import com.yoon.nodeproject2.widget.utils.SingleLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository,
    val paging: Paging<Subject>

) : ViewModel() {

    private val userId = CheckuApplication.prefs.getUserId()

    var searchQuery = ""

    private val _subjectList = MutableLiveData<List<Subject?>>()
    val subjectList: LiveData<List<Subject?>> = _subjectList

    private val _subjectErrorToastEvent = MutableSingleLiveData<Boolean>()
    val subjectErrorToastEvent: SingleLiveData<Boolean> = _subjectErrorToastEvent

    private val _subjectWaitEvent = MutableSingleLiveData<Boolean>()
    val subjectWaitEvent: SingleLiveData<Boolean> = _subjectWaitEvent

    private val _updateRecyclerViewItemEvent = MutableSingleLiveData<Pair<Int, Subject>>()
    val updateRecyclerViewItemEvent: SingleLiveData<Pair<Int, Subject>> = _updateRecyclerViewItemEvent

    fun changeSubject() {
        _subjectWaitEvent.setValue(true)
        paging.resetPage()

        viewModelScope.launch {
            val response = searchRepository.getSubjectBySearch(userId, searchQuery, paging.page.value ?: 0);
            if (response is ApiResponse.Success) {
                response.onSuccess {
                    paging.loadData(
                        this.data.contents.toMutableList(),
                        this.data.last, _subjectList,
                        paging.changeData()
                    )
                }
            } else {
                _subjectErrorToastEvent.setValue(true)
            }
        }
    }

    fun searchSubject() {
        if (paging.isLastPage) {
            return
        }

        viewModelScope.launch {
            val response = searchRepository.getSubjectBySearch(userId, searchQuery, paging.page.value ?: 0);
            if (response is ApiResponse.Success) {
                response.onSuccess {
                    paging.loadData(
                        this.data.contents.toMutableList(),
                        this.data.last, _subjectList,
                        paging.addData()
                    )
                }
            } else {
                _subjectErrorToastEvent.setValue(true)
            }
        }
    }

    fun addOrRemoveSubject(subject: Subject, position: Int) {
        viewModelScope.launch {
            val response =
                searchRepository.addOrRemoveSubject(AddOrRemoveSubjectRequest(userId, subject.subjectNumber))
            response.onSuccess {
                setChecked(subject, position)
            }.onFailure {

            }
        }
    }

    private fun setChecked(subject: Subject, position: Int) {
        subject.isMySubject = !subject.isMySubject
        _updateRecyclerViewItemEvent.setValue(Pair(position, subject))
    }


}