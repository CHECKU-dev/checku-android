package com.example.nodeproject2.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.nodeproject2.R
import com.example.nodeproject2.data.model.LoginRequest
import com.example.nodeproject2.repository.LoginRepository
import com.example.nodeproject2.ui.MainActivity
import com.skydoves.sandwich.ApiResponse
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.math.log

@AndroidEntryPoint
@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    @Inject lateinit var loginRepository: LoginRepository

    companion object {
        private const val SPLASH_DELAY_TIME = 2000L
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        login()

    }

    private fun login() {
        val intent = Intent(baseContext, MainActivity::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            delay(SPLASH_DELAY_TIME)
            val request = LoginRequest("cWgBoOvyQAyRGI91NS63Wt:APA91bE5cJShrVR-wGXJBcqZLBX7AjsG4p8e3xr2VqV_cepUHPrsDjbl5dzReapVZ9OZegdbCHEDMo_yW6iJZhfp2jRyZUXVqM0zsroYSo3-WiEeGt8fOaTp16ecqLjM1BHXMiDg4-B4")
            val login = loginRepository.login(request)

            if (login is ApiResponse.Success) {
                Log.d("SplashActivity","fcmToken: ${login.data.fcmToken}, userId: ${login.data.userId}")
            }
            withContext(Dispatchers.Main) {
                startActivity(intent)
                finish()
            }

        }
    }

}