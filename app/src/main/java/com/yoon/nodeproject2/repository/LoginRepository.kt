package com.yoon.nodeproject2.repository

import com.yoon.nodeproject2.data.model.LoginRequest
import com.yoon.nodeproject2.data.remote.api.Api
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginRepository @Inject constructor(private val api: Api){

    suspend fun login(loginRequest: LoginRequest) = api.login(loginRequest)

}