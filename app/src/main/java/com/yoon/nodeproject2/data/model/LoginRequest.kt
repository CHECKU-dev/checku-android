package com.yoon.nodeproject2.data.model

import com.google.gson.annotations.SerializedName

data class LoginRequest(
//    @SerializedName("userId") val userId: Long,
    @SerializedName("fcmToken") val fcmToken: String
)
