package com.example.nodeproject2.di

import com.example.nodeproject2.data.remote.api.LoginApi
import com.example.nodeproject2.widget.utils.Utils.BASE_URL
import com.skydoves.sandwich.adapters.ApiResponseCallAdapterFactory
import okhttp3.logging.HttpLoggingInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(getLoggingInterceptor())
            .build()
    }

//    @Singleton
//    @Provides
//    fun provideRetrofitInstance(
//        gsonConverterFactory: GsonConverterFactory
//    ): Retrofit {
//        return Retrofit.Builder()
//            .baseUrl(BASE_URL)
//            //json 변화기 Factory
//            .client(provideHttpClient())
////            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//            .addConverterFactory(gsonConverterFactory)
//            .build()
//
//    }

    @Provides
    @Singleton
    fun provideConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }


    private fun getLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }


    @Provides
    @Singleton
    fun provideLoginApiService(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): LoginApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addCallAdapterFactory(ApiResponseCallAdapterFactory.create())
            .addConverterFactory(gsonConverterFactory)
            .build()
            .create(LoginApi::class.java)
    }


}