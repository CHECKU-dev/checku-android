package com.yoon.nodeproject2.ui.list.subject

import android.view.View
import android.widget.AdapterView
import android.widget.CompoundButton
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
import com.yoon.nodeproject2.repository.SubjectRepository
import com.yoon.nodeproject2.ui.list.subject.model.SubjectGrade
import com.yoon.nodeproject2.ui.list.subject.model.SubjectType
import com.yoon.nodeproject2.widget.utils.MutableSingleLiveData
import com.yoon.nodeproject2.widget.utils.SingleLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SubjectViewModel @Inject constructor(
    private val subjectRepository: SubjectRepository,
) : ViewModel() {


    private val userId = CheckuApplication.prefs.getUserId()

    private val _subjectList = MutableLiveData<List<Subject?>>()
    val subjectList: LiveData<List<Subject?>> = _subjectList

    private val _subjectErrorToastEvent = MutableSingleLiveData<Boolean>()
    val subjectErrorToastEvent: SingleLiveData<Boolean> = _subjectErrorToastEvent

    private val _subjectWaitEvent = MutableSingleLiveData<Boolean>()
    val subjectWaitEvent: SingleLiveData<Boolean> = _subjectWaitEvent

    private val _refreshed = MutableLiveData<Boolean>()
    val refreshed: LiveData<Boolean> = _refreshed

    private val _department = MutableLiveData<String>("공과대학_컴퓨터공학부")
    private val department: LiveData<String> = _department

    private val _grade = MutableLiveData<SubjectGrade>(SubjectGrade.ALL)
    private val grade: LiveData<SubjectGrade> = _grade

    private val _type = MutableLiveData<SubjectType>(SubjectType.ALL)
    val type: LiveData<SubjectType> = _type

    private val _vacancy = MutableLiveData<Boolean>(false)
    val vacancy: LiveData<Boolean> = _vacancy

    private val _updateRecyclerViewItemEvent = MutableSingleLiveData<Pair<Int, Subject>>()
    val updateRecyclerViewItemEvent: SingleLiveData<Pair<Int, Subject>> = _updateRecyclerViewItemEvent

    // TODO 학과명 변환
    fun onSelectItem(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
        _department.value = parent!!.selectedItem.toString()
            .replace(" ", "_")
            .replace("(", "_")
            .replace(")", "")
            .replace("·", "_")
        getSubjectData()
    }

    fun getSubjectData() {
        _subjectWaitEvent.setValue(true)

        viewModelScope.launch {
            val mySubjectsResponse =
                subjectRepository.getSubjects(
                    userId,
                    department.value!!,
                    grade.value!!.name,
                    type.value!!.name,
                    vacancy.value!!
                )
            if (mySubjectsResponse is ApiResponse.Success) {
//                _subjectList.value = mySubjectsResponse.data
                _subjectList.value = MutableList(mySubjectsResponse.data.size) { mySubjectsResponse.data[it] }
            } else {
                _subjectErrorToastEvent.setValue(true)
            }
        }
    }

    fun refreshData() {
        _refreshed.value = true
        getSubjectData()
        _refreshed.value = false
    }

    fun addOrRemoveSubject(subject: Subject, position: Int) {
        viewModelScope.launch {
            val response =
                subjectRepository.addOrRemoveSubject(AddOrRemoveSubjectRequest(userId, subject.subjectNumber))
            response.onSuccess {
                setChecked(subject, position)
            }.onFailure {

            }
        }
    }

    private fun setChecked(subject: Subject, position: Int) {
        _subjectList.value?.forEach {
            if (it != null) {
                if (it == subject) {
                    it.isMySubject = !it.isMySubject
                }
            }
        }
        _subjectList.value = _subjectList.value
        _updateRecyclerViewItemEvent.setValue(Pair(position, subject))
    }


    fun updateDepartment(department: String) {
        _department.value = department
        getSubjectData()
    }

    fun updateSubjectGrade(grade: SubjectGrade) {
        _grade.value = grade
        getSubjectData()
    }

    fun updateSubjectType(type: SubjectType) {
        _type.value = type
        getSubjectData()
    }

    fun onCheckboxVacancyChange(button: CompoundButton, check: Boolean) {
        _vacancy.value = check
        getSubjectData()
    }


}