package com.example.nodeproject2.data.remote.api

import com.example.nodeproject2.data.model.*
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface Api {

    @POST("/api/users")
    suspend fun login(@Body request: LoginRequest): ApiResponse<LoginResponse>

    @GET("/api/my-subjects")
    suspend fun getMySubjects(@Query("subjects") subjects: List<String>): ApiResponse<GetSubjectsResponse>

    @GET("/api/subjects")
    suspend fun getSubjects(
        @Query("department") department: String,
        @Query("grade") grade: String,
        @Query("type") type: String
    ): ApiResponse<GetSubjectsResponse>

    @POST("/api/notification")
    suspend fun applyNotification(
        @Body request: NotificationRequest
    ): ApiResponse<NotificationResponse>

    @DELETE("/api/my-subjects")
    suspend fun removeSubject(
        @Body request: RemoveSubjectRequest
    ): ApiResponse<NotificationResponse>

}
