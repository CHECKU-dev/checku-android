package com.example.nodeproject2.di

import android.app.Application
import android.app.Dialog
import com.example.nodeproject2.widget.utils.Utils.BASE_URL
import dagger.hilt.android.HiltAndroidApp
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@HiltAndroidApp
class CheckuApplication :Application(){
    companion object {
//        lateinit var prefs: PrefsManager
//        lateinit var encryptedPrefs: EncryptedPrefsManger
        var loadingDialog: Dialog? = null
        lateinit var instance: CheckuApplication
        lateinit var sRetrofit: Retrofit

    }

    override fun onCreate() {
//        prefs = PrefsManager(applicationContext)
//        encryptedPrefs = EncryptedPrefsManger(applicationContext)
        initRetrofitInstance()
        instance = this
        super.onCreate()
    }

    private fun initRetrofitInstance() {
        val client: OkHttpClient = OkHttpClient.Builder()
            .readTimeout(5000, TimeUnit.MILLISECONDS)
            .connectTimeout(5000, TimeUnit.MILLISECONDS)
            // 로그캣에 okhttp.OkHttpClient로 검색하면 http 통신 내용을 보여줍니다.
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()

        // sRetrofit 이라는 전역변수에 API url, 인터셉터, Gson을 넣어주고 빌드해주는 코드
        // 이 전역변수로 http 요청을 서버로 보내면 됩니다.
        sRetrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


}