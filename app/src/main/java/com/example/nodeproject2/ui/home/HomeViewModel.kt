package com.example.nodeproject2.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nodeproject2.data.model.Notification
import com.example.nodeproject2.di.CheckuApplication
import com.example.nodeproject2.repository.NotificationRepository
import com.example.nodeproject2.widget.utils.MutableSingleLiveData
import com.example.nodeproject2.widget.utils.SingleLiveData
import com.skydoves.sandwich.ApiResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val notificationRepository: NotificationRepository
) : ViewModel() {

    private val userId = CheckuApplication.prefs.getUserId()

    private val _notificationList = MutableLiveData<MutableList<Notification>>()
    val notificationList: LiveData<MutableList<Notification>> = _notificationList

    private val _homeErrorToastEvent = MutableSingleLiveData<Boolean>()
    val homeErrorToastEvent: SingleLiveData<Boolean> = _homeErrorToastEvent

    private val _homeWaitEvent = MutableSingleLiveData<Boolean>()
    val homeWaitEvent: SingleLiveData<Boolean> = _homeWaitEvent

    fun getInitData() {
        _homeWaitEvent.setValue(true)
        viewModelScope.launch {

            val mynotification = notificationRepository.getNotifications(userId)

            if (mynotification is ApiResponse.Success) {
                _notificationList.value = MutableList(mynotification.data.size) { mynotification.data[it] }
            } else {
                _homeErrorToastEvent.setValue(true)
            }
        }
    }

}