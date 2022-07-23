package com.example.nodeproject2.di

import android.app.Application
import android.app.Dialog
import com.example.nodeproject2.widget.utils.PrefsManager
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CheckuApplication :Application(){
    companion object {
        lateinit var prefs: PrefsManager
        var loadingDialog: Dialog? = null
        lateinit var instance: CheckuApplication

    }

    override fun onCreate() {
        prefs = PrefsManager(applicationContext)
        instance = this
        super.onCreate()
    }



}