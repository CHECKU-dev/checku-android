package com.example.nodeproject2.di

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.nodeproject2.data.remote.api.Api
import com.example.nodeproject2.widget.utils.NullOnEmptyConverterFactory
import com.example.nodeproject2.widget.utils.Utils.BASE_URL
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import com.skydoves.sandwich.adapters.ApiResponseCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
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

    @RequiresApi(Build.VERSION_CODES.O)
    @Provides
    @Singleton
    fun provideConverterFactory(): GsonConverterFactory {
        val gson = GsonBuilder()
            .registerTypeAdapter(
                LocalDateTime::class.java,
                JsonDeserializer<Any?> { json, typeOfT, context ->
                    LocalDateTime.parse(
                        json.asString,
                        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
                    )
                })
            .registerTypeAdapter(
                LocalDate::class.java,
                JsonDeserializer<Any?> { json, typeOfT, context ->
                    LocalDate.parse(
                        json.asString,
                        DateTimeFormatter.ofPattern("yyyy-MM-dd")
                    )
                })
            .registerTypeAdapter(
                LocalTime::class.java,
                JsonDeserializer<Any?> { json, typeOfT, context ->
                    LocalTime.parse(
                        json.asString,
                        DateTimeFormatter.ofPattern("HH:mm:ss")
                    )
                })
            .create()
        return GsonConverterFactory.create(gson);
    }



    private fun getLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }


    @Provides
    @Singleton
    fun provideLoginApiService(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Api {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(NullOnEmptyConverterFactory())
            .addCallAdapterFactory(ApiResponseCallAdapterFactory.create())
            .addConverterFactory(gsonConverterFactory)
            .build()
            .create(Api::class.java)
    }


}