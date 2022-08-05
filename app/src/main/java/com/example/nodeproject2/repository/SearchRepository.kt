package com.example.nodeproject2.repository

import com.example.nodeproject2.data.model.AddOrRemoveSubjectRequest
import com.example.nodeproject2.data.remote.api.Api
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchRepository @Inject constructor(private val api: Api){

    suspend fun getSubjectBySearch(
        searchQuery: String,
        page: Int?
    ) = api.getSubjectBySearch(searchQuery, page)

    suspend fun addOrRemoveSubject(
        request: AddOrRemoveSubjectRequest
    ) = api.addOrRemoveSubject(request)

}