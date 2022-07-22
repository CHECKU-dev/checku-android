package com.example.nodeproject2.data.model

import com.google.gson.annotations.SerializedName

//TODO 삭제해도 될듯
data class NotificationResponse(

    @SerializedName("notificationId") val notificationId: Long
)
