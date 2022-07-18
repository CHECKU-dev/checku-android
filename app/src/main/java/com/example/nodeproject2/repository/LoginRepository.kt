package com.example.nodeproject2.repository

import com.example.nodeproject2.data.model.LoginRequest
import com.example.nodeproject2.data.remote.api.LoginApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginRepository @Inject constructor(private val loginApi: LoginApi){

    suspend fun login(loginRequest: LoginRequest) = loginApi.login(loginRequest)

}