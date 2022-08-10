package com.yoon.nodeproject2.widget.utils

import android.content.Context

class PrefsManager(context: Context) {
    private val prefs = context.getSharedPreferences("user", Context.MODE_PRIVATE)

    fun saveUserId(value: Long) = setLong(USER_ID, value)

    fun getUserId() = getLong(USER_ID)

    private fun getLong(key: String) = prefs.getLong(key, 0L)

    private fun setLong(key: String, value: Long) = prefs.edit().putLong(key, value).apply()

    companion object {
        private const val USER_ID = "userId"
    }


}