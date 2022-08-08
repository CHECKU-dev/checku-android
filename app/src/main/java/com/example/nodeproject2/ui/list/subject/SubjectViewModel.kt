package com.example.nodeproject2.ui.list.subject

import android.view.View
import android.widget.AdapterView
import android.widget.CompoundButton
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nodeproject2.data.model.AddOrRemoveSubjectRequest
import com.example.nodeproject2.data.model.Subject
import com.example.nodeproject2.di.CheckuApplication
import com.example.nodeproject2.repository.SubjectRepository
import com.example.nodeproject2.ui.list.subject.model.SubjectGrade
import com.example.nodeproject2.ui.list.subject.model.SubjectType
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




    fun onSelectItem(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
        _department.value = parent!!.selectedItem.toString().replace(" ", "_")
        getSubjectData()

        //pos                                 get selected item position
        //view.getText()                      get lable of selected item
        //parent.getAdapter().getItem(pos)    get item by pos
        //parent.getAdapter().getCount()      get item count
        //parent.getCount()                   get item count
        //parent.getSelectedItem()            get selected item
        //and other...
    }

    fun getSubjectData() {
        _subjectWaitEvent.setValue(true)

        // 플로팅 버튼 전공일 경우
        viewModelScope.launch {
            //TODO null 체크 확인해보기
            val mySubjectsResponse =
                subjectRepository.getSubjects(
                    userId,
                    department.value!!,
                    grade.value!!.name,
                    type.value!!.name,
                    vacancy.value!!
                )
            if (mySubjectsResponse is ApiResponse.Success) {
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

    fun addOrRemoveSubject(subjectNumber: String) {
        viewModelScope.launch {
            val response = subjectRepository.addOrRemoveSubject(AddOrRemoveSubjectRequest(userId, subjectNumber))
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