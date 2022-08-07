package com.example.nodeproject2.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nodeproject2.data.model.AddOrRemoveSubjectRequest
import com.example.nodeproject2.data.model.Notification
import com.example.nodeproject2.data.model.Subject
import com.example.nodeproject2.di.CheckuApplication
import com.example.nodeproject2.repository.NotificationRepository
import com.example.nodeproject2.repository.SearchRepository
import com.example.nodeproject2.widget.utils.MutableSingleLiveData
import com.example.nodeproject2.widget.utils.Paging
import com.example.nodeproject2.widget.utils.SingleLiveData
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.onFailure
import com.skydoves.sandwich.onSuccess
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
        } else {
            _subjectWaitEvent.setValue(true)
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

    fun addOrRemoveSubject(subjectNumber: String) {
        viewModelScope.launch {
            val response = searchRepository.addOrRemoveSubject(AddOrRemoveSubjectRequest(userId, subjectNumber))
            response.onSuccess {
                setChecked(subjectNumber, true)
            }.onFailure {
                setChecked(subjectNumber, false)
            }
        }
    }

    private fun setChecked(subjectNumber: String, success: Boolean) {
        if (success) {
            _subjectList.value?.forEach {
                if (it != null) {
                    if (it!!.subjectNumber == subjectNumber) {
                        it.isMySubject = !it.isMySubject
                        return@forEach
                    }
                }

            }
            _subjectList.value = _subjectList.value
        }
    }


}