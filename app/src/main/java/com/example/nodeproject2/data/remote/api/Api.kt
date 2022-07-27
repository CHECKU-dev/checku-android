package com.example.nodeproject2.data.remote.api

import com.example.nodeproject2.data.model.*
import com.example.nodeproject2.data.model.GetScheduleResponse
import com.example.nodeproject2.data.model.dao.GetNotificationResponse
import com.google.gson.JsonElement
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
    suspend fun getMySubjects(@Query("userId") userId: Long): ApiResponse<GetSubjectsResponse>

    @GET("/api/subjects")
    suspend fun getSubjects(
        @Query("department") department: String,
        @Query("grade") grade: String,
        @Query("type") type: String
    ): ApiResponse<GetSubjectsResponse>

    @POST("/api/notification")
    suspend fun applyNotification(
        @Body request: NotificationRequest
    ): ApiResponse<JsonElement>

    @POST("/api/my-subjects")
    suspend fun addSubject(
        @Body request: AddSubjectRequest
    ): ApiResponse<JsonElement>

    @DELETE("/api/my-subjects")
    suspend fun removeSubject(
        @Query("userId") userId: Long,
        @Query("subjectNumber") subjectNumber: String
    ): ApiResponse<JsonElement>

    @GET("/api/schedule")
    suspend fun getSchedule() : ApiResponse<GetScheduleResponse>

    //TODO 확인
    @GET("/api/notification")
    suspend fun getNotifications(@Query("userId") userId: Long): ApiResponse<GetNotificationResponse>

    @DELETE("/api/notification")
    suspend fun cancelNotification(
        @Query("userId") userId: Long,
        @Query("subjectNumber") subjectNumber: String
    ): ApiResponse<JsonElement>


}
