package com.example.nodeproject2.di

import android.app.Activity
import android.app.Application
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import com.example.nodeproject2.R
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

    fun showLoadingDialog(activity: Activity) {
        if(activity.isFinishing || loadingDialog?.isShowing == true)    return
        loadingDialog = Dialog(activity)
        loadingDialog?.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        loadingDialog?.window!!.setDimAmount(0f)
        loadingDialog?.setContentView(R.layout.dialog_loading)
        loadingDialog?.setCanceledOnTouchOutside(false)
        loadingDialog?.setOnCancelListener { activity.onBackPressed() }
        loadingDialog?.show()
    }

    fun hideLoadingDialog() {
        if(loadingDialog?.isShowing == true)
            loadingDialog?.dismiss()
    }



}