package com.yoon.nodeproject2.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skydoves.sandwich.ApiResponse
import com.yoon.nodeproject2.data.model.Notification
import com.yoon.nodeproject2.di.CheckuApplication
import com.yoon.nodeproject2.repository.NotificationRepository
import com.yoon.nodeproject2.widget.utils.MutableSingleLiveData
import com.yoon.nodeproject2.widget.utils.SingleLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val notificationRepository: NotificationRepository
) : ViewModel() {

    private var subjectNumber = ""

    private val userId = CheckuApplication.prefs.getUserId()

    private val _notificationList = MutableLiveData<MutableList<Notification>>()
    val notificationList: LiveData<MutableList<Notification>> = _notificationList

    private val _homeErrorToastEvent = MutableSingleLiveData<Boolean>()
    val homeErrorToastEvent: SingleLiveData<Boolean> = _homeErrorToastEvent

    private val _homeSuccessToastEvent = MutableSingleLiveData<Boolean>()
    val homeSuccessToastEvent: SingleLiveData<Boolean> = _homeSuccessToastEvent

    private val _homeWaitEvent = MutableSingleLiveData<Boolean>()
    val homeWaitEvent: SingleLiveData<Boolean> = _homeWaitEvent

    private val _dialogShowEvent = MutableSingleLiveData<Notification>()
    val dialogShowEvent: SingleLiveData<Notification> = _dialogShowEvent

    private val _dialogDismissEvent = MutableSingleLiveData<Boolean>()
    val dialogDismissEvent: SingleLiveData<Boolean> = _dialogDismissEvent



    fun getInitData() {
        _homeWaitEvent.setValue(true)
        viewModelScope.launch {

            val myNotification = notificationRepository.getNotifications(userId)

            if (myNotification is ApiResponse.Success) {
                _notificationList.value = MutableList(myNotification.data.size) { myNotification.data[it] }

            } else {
                _homeErrorToastEvent.setValue(true)
            }
        }
    }

    fun cancelNotification() {
        _dialogDismissEvent.setValue(true)

        viewModelScope.launch {
            val response = notificationRepository.cancelNotification(userId, subjectNumber)
            if (response is ApiResponse.Success) {
                _homeSuccessToastEvent.setValue(true)
            } else {
                _homeErrorToastEvent.setValue(true)
            }
            getInitData()

        }
    }

    fun showDialog(notification: Notification) {
        this.subjectNumber = notification.subjectNumber
        _dialogShowEvent.setValue(notification)
    }

    fun dismissBottomSheetDialog() {
        this.subjectNumber = ""
        _dialogDismissEvent.setValue(true)
    }




}