package com.example.nodeproject2.data.remote.api

import com.example.nodeproject2.data.model.GetSubjectsResponse
import com.example.nodeproject2.data.model.LoginRequest
import com.example.nodeproject2.data.model.LoginResponse
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface LoginApi {

    @POST("/api/users")
    suspend fun login(@Body request: LoginRequest): ApiResponse<LoginResponse>

    @GET("/api/my-subjects")
    suspend fun getMySubjects(@Query("subjects") subjects: List<String>): ApiResponse<GetSubjectsResponse>


}
