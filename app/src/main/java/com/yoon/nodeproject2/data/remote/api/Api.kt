package com.yoon.nodeproject2.data.remote.api

import com.google.gson.JsonElement
import com.skydoves.sandwich.ApiResponse
import com.yoon.nodeproject2.data.model.*
import com.yoon.nodeproject2.data.model.dao.GetNotificationResponse
import org.json.JSONObject
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
        @Query("userId") userId: Long,
        @Query("department") department: String,
        @Query("grade") grade: String,
        @Query("type") type: String,
        @Query("vacancy") vacancy: Boolean
    ): ApiResponse<GetSubjectsResponse>

    @POST("/api/notification")
    suspend fun applyNotification(
        @Body request: NotificationRequest
    ): ApiResponse<JsonElement>

    @POST("/api/my-subjects")
    suspend fun addOrRemoveSubject(
        @Body request: AddOrRemoveSubjectRequest
    ): ApiResponse<JsonElement>

    @GET("/api/schedule")
    suspend fun getSchedule(): ApiResponse<GetScheduleResponse>

    @GET("/api/notification")
    suspend fun getNotifications(@Query("userId") userId: Long): ApiResponse<GetNotificationResponse>

    @DELETE("/api/notification")
    suspend fun cancelNotification(
        @Query("userId") userId: Long,
        @Query("subjectNumber") subjectNumber: String
    ): ApiResponse<JsonElement>


    @GET("/api/subjects/search")
    suspend fun getSubjectBySearch(
        @Query("userId") userId: Long,
        @Query("searchQuery") searchQuery: String,
        @Query("page") page: Int? = 0
    ): ApiResponse<SubjectListDto>


}
