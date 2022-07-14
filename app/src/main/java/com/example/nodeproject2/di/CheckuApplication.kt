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

    }

    override fun onCreate() {
//        prefs = PrefsManager(applicationContext)
//        encryptedPrefs = EncryptedPrefsManger(applicationContext)
//        initRetrofitInstance()
        instance = this
        super.onCreate()
    }



}