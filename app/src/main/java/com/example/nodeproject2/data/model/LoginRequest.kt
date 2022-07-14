package com.example.nodeproject2.data.model

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("email") val email:String
//    @SerializedName("fcmToken") val fcmToken: String
)
