package com.example.nodeproject2.di

import android.app.Application
import android.app.Dialog
import dagger.hilt.android.HiltAndroidApp

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