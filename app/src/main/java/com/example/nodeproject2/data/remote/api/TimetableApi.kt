package com.example.nodeproject2.data.remote.api

import com.example.nodeproject2.data.model.GetSubjectsResponse
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.*

interface TimetableApi {

    @GET("/api/my-subjects")
    suspend fun getMySubjects(@Part subjects: List<String>): ApiResponse<GetSubjectsResponse>

}
