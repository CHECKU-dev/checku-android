package com.example.nodeproject2.data.remote.api

import com.example.nodeproject2.data.model.LoginRequest
import com.example.nodeproject2.data.model.LoginResponse
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.*

interface LoginApi {

    @POST("/api/users")
    suspend fun login(@Body request: LoginRequest): ApiResponse<LoginResponse>

}
