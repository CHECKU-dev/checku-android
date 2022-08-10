package com.yoon.nodeproject2.repository

import com.yoon.nodeproject2.data.model.AddOrRemoveSubjectRequest
import com.yoon.nodeproject2.data.remote.api.Api
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchRepository @Inject constructor(private val api: Api){

    suspend fun getSubjectBySearch(
        userId: Long,
        searchQuery: String,
        page: Int?
    ) = api.getSubjectBySearch(userId, searchQuery, page)

    suspend fun addOrRemoveSubject(
        request: AddOrRemoveSubjectRequest
    ) = api.addOrRemoveSubject(request)

}