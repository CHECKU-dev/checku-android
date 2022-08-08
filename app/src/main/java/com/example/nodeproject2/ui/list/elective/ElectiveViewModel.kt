package com.example.nodeproject2.ui.list.elective

import android.widget.CompoundButton
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nodeproject2.data.model.AddOrRemoveSubjectRequest
import com.example.nodeproject2.data.model.Subject
import com.example.nodeproject2.di.CheckuApplication
import com.example.nodeproject2.repository.SubjectRepository
import com.example.nodeproject2.ui.list.elective.model.ElectiveType
import com.example.nodeproject2.widget.utils.MutableSingleLiveData
import com.example.nodeproject2.widget.utils.SingleLiveData
import com.skydoves.sandwich.ApiResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ElectiveViewModel @Inject constructor(
    private val subjectRepository: SubjectRepository
) : ViewModel() {

    private val userId = CheckuApplication.prefs.getUserId()

    private val _subjectList = MutableLiveData<MutableList<Subject>>()
    val subjectList: LiveData<MutableList<Subject>> = _subjectList

    private val _subjectErrorToastEvent = MutableSingleLiveData<Boolean>()
    val subjectErrorToastEvent: SingleLiveData<Boolean> = _subjectErrorToastEvent

    private val _subjectWaitEvent = MutableSingleLiveData<Boolean>()
    val subjectWaitEvent: SingleLiveData<Boolean> = _subjectWaitEvent

    private val _refreshed = MutableLiveData<Boolean>()
    val refreshed: LiveData<Boolean> = _refreshed

    private val _type = MutableLiveData<ElectiveType>(ElectiveType.BASIC_ELECTIVE)
    val type: LiveData<ElectiveType> = _type

    private val _vacancy = MutableLiveData<Boolean>(false)
    val vacancy: LiveData<Boolean> = _vacancy

    fun getElectives() {
        _subjectWaitEvent.setValue(true)

        viewModelScope.launch {
            val mySubjectsResponse =
                subjectRepository.getSubjects(userId, "교양", "ALL", type.value!!.name, vacancy.value!!)
            if (mySubjectsResponse is ApiResponse.Success) {
                _subjectList.value = MutableList(mySubjectsResponse.data.size) { mySubjectsResponse.data[it] }
            } else {
                _subjectErrorToastEvent.setValue(true)
            }
        }
    }

    fun refreshData() {
        _refreshed.value = true
        getElectives()
        _refreshed.value = false
    }

    //TODO 변경!
    fun addOrRemoveSubject(subjectNumber: String) {
        viewModelScope.launch {
            subjectRepository.addOrRemoveSubject(AddOrRemoveSubjectRequest(userId, subjectNumber))
        }
    }

    fun updateElectiveType(type: ElectiveType) {
        _type.value = type
        getElectives()
    }

    fun onCheckboxVacancyChange(button: CompoundButton, check: Boolean) {
        _vacancy.value = check
        getElectives()
    }

}