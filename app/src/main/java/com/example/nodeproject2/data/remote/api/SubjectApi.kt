package com.example.nodeproject2.data.remote.api

import com.example.nodeproject2.data.model.GetSubjectsResponse
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.*

interface SubjectApi {

    @GET("/api/my-subjects")
    fun getMySubjects(@Part subjects: List<String>): ApiResponse<GetSubjectsResponse>

}
