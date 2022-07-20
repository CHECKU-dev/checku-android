package com.example.nodeproject2.repository

import com.example.nodeproject2.data.model.LoginRequest
import com.example.nodeproject2.data.remote.api.Api
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginRepository @Inject constructor(private val api: Api){

    suspend fun login(loginRequest: LoginRequest) = api.login(loginRequest)

}