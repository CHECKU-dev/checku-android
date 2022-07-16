package com.example.nodeproject2.data.remote.api

import com.example.nodeproject2.data.model.LoginRequest
import com.example.nodeproject2.data.model.LoginResponse
import com.skydoves.sandwich.ApiResponse
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.POST
import retrofit2.http.PartMap

interface LoginApi {

    @POST("/api/users")
    suspend fun login(@Body request: LoginRequest): ApiResponse<LoginResponse>

}
