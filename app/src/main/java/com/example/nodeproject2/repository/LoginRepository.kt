package com.example.nodeproject2.repository

import com.example.nodeproject2.data.model.LoginRequest
import com.example.nodeproject2.data.remote.api.LoginApi
import okhttp3.RequestBody
import retrofit2.http.Field
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginRepository @Inject constructor(private val loginApi: LoginApi){

    suspend fun login(
        @Field("email") email: String) = loginApi.login(email)

}