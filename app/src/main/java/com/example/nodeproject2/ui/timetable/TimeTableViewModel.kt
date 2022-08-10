package com.example.nodeproject2.ui.timetable

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nodeproject2.data.model.AddOrRemoveSubjectRequest
import com.example.nodeproject2.data.model.NotificationRequest
import com.example.nodeproject2.data.model.Subject
import com.example.nodeproject2.di.CheckuApplication
import com.example.nodeproject2.repository.TimetableRepository
import com.example.nodeproject2.widget.utils.MutableSingleLiveData
import com.example.nodeproject2.widget.utils.SingleLiveData
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.skydoves.sandwich.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject


@HiltViewModel
class TimeTableViewModel @Inject constructor(
    private val timetableRepository: TimetableRepository
) : ViewModel() {

    private val gson = Gson()

    private val userId = CheckuApplication.prefs.getUserId()

    private val _subjectList = MutableLiveData<MutableList<Subject>>()
    val subjectList: LiveData<MutableList<Subject>> = _subjectList

    private val _notificationApplySuccessEvent = MutableSingleLiveData<Boolean>()
    val notificationApplySuccessEvent: SingleLiveData<Boolean> = _notificationApplySuccessEvent

    private val _subjectRemoveSuccessEvent = MutableSingleLiveData<Boolean>()
    val subjectRemoveSuccessEvent: SingleLiveData<Boolean> = _subjectRemoveSuccessEvent

    private val _timeTableErrorToastEvent = MutableSingleLiveData<Boolean>()
    val timeTableErrorToastEvent: SingleLiveData<Boolean> = _timeTableErrorToastEvent

    private val _notificationErrorToastEvent = MutableSingleLiveData<String>()
    val notificationErrorToastEvent: SingleLiveData<String> = _notificationErrorToastEvent

    private val _timeTableWaitEvent = MutableSingleLiveData<Boolean>()
    val timeTableWaitEvent: SingleLiveData<Boolean> = _timeTableWaitEvent

    private val _refreshed = MutableLiveData<Boolean>()
    val refreshed: LiveData<Boolean> = _refreshed


    init {
        getInitData()
    }

    fun getInitData() {
        _timeTableWaitEvent.setValue(true)
        viewModelScope.launch {
            val mySubjectsResponse = timetableRepository.getMySubjects(userId)
            if (mySubjectsResponse is ApiResponse.Success) {
                _subjectList.value = MutableList(mySubjectsResponse.data.size) { mySubjectsResponse.data[it] }
            } else {
                _timeTableErrorToastEvent.setValue(true)
            }

        }
    }

    fun refreshData() {
        _refreshed.value = true
        getInitData()
        _refreshed.value = false
    }


    fun removeSubject(subjectNumber: String) {
        viewModelScope.launch {
            val response = timetableRepository.removeSubject(AddOrRemoveSubjectRequest(userId, subjectNumber))
            if (response is ApiResponse.Success) {
                _subjectRemoveSuccessEvent.setValue(true)
                getInitData()
            } else {
                _timeTableErrorToastEvent.setValue(true)
            }

        }
    }


    fun applyNotification(subject: Subject) {
        _timeTableWaitEvent.setValue(true)

        viewModelScope.launch {

            val request = NotificationRequest(userId, subject.subjectNumber, subject.subjectName, subject.professor)
            val response = timetableRepository.applyNotification(request)
            response.onSuccess {
                _notificationApplySuccessEvent.setValue(true)
            }
                .onError {
                    val jsonParser = JsonParser()
                    val parse = jsonParser.parse(this.errorBody?.string())
                    val jsonObj = parse.asJsonObject
                    _notificationErrorToastEvent.setValue(
                        jsonObj.get("errorMessages").asString
                    )
                }

        }

    }


}