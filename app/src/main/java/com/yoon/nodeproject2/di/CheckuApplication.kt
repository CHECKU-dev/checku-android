package com.yoon.nodeproject2.di

import android.app.Activity
import android.app.Application
import android.app.Dialog
import androidx.room.Room
import com.yoon.nodeproject2.R
import com.yoon.nodeproject2.widget.utils.AppDatabase
import com.yoon.nodeproject2.widget.utils.PrefsManager
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CheckuApplication : Application() {
    companion object {
        lateinit var prefs: PrefsManager

        var loadingDialog: Dialog? = null

        lateinit var instance: CheckuApplication

        lateinit var dataBaseInstance: AppDatabase
    }

    override fun onCreate() {
        prefs = PrefsManager(applicationContext)
        instance = this

        dataBaseInstance = Room.databaseBuilder(
            instance.applicationContext,
            AppDatabase::class.java,
            "schedule.db"
        )
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
        super.onCreate()
    }

    fun showLoadingDialog(activity: Activity) {
        if (activity.isFinishing || loadingDialog?.isShowing == true) return
        loadingDialog = Dialog(activity)
        loadingDialog?.setContentView(R.layout.dialog_progress)
        loadingDialog?.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        loadingDialog?.setCancelable(false)
        loadingDialog?.setCanceledOnTouchOutside(false)
        loadingDialog?.setOnCancelListener { activity.onBackPressed() }
        loadingDialog?.show()

    }

    fun hideLoadingDialog() {
        if (loadingDialog?.isShowing == true)
            loadingDialog?.dismiss()
    }


}