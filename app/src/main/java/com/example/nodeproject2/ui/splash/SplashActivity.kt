package com.example.nodeproject2.ui.splash

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.nodeproject2.data.model.LoginRequest
import com.example.nodeproject2.databinding.ActivitySplashBinding
import com.example.nodeproject2.di.CheckuApplication
import com.example.nodeproject2.repository.LoginRepository
import com.example.nodeproject2.repository.ScheduleRepository
import com.example.nodeproject2.ui.MainActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.skydoves.sandwich.ApiResponse
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    lateinit var binding: ActivitySplashBinding

    @Inject
    lateinit var loginRepository: LoginRepository

    @Inject
    lateinit var scheduleRepository: ScheduleRepository

    private val TAG = "SplashActivity.class"

    companion object {
        private const val SPLASH_DELAY_TIME = 2000L
    }

    //TODO 서버 통신 실패 알림 추가
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val splashAnimation = AnimationUtils.loadAnimation(this, R.anim.activity_fade_in)
//        binding.splashImg.startAnimation(splashAnimation)
//        binding.splashText.startAnimation(splashAnimation)

        start()
    }

    private fun start(): String? {
        var fcmToken: String? = null

        val userId = CheckuApplication.prefs.getUserId() ?: ""


        if (userId == 0L) {
            getSchedule()

            FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                    return@OnCompleteListener
                }
                // Get new FCM registration token
                fcmToken = task.result

                fcmToken?.let {
                    login(LoginRequest(it))
                }

                Log.d(TAG, "FCM: $fcmToken")
            })
        } else {
            CoroutineScope(Dispatchers.IO).launch {
                delay(SPLASH_DELAY_TIME)

                val intent = Intent(baseContext, MainActivity::class.java)

                withContext(Dispatchers.Main) {
                    startActivity(intent)
                    finish()
                }
            }
        }

        return fcmToken
    }

    private fun getSchedule() {
        CoroutineScope(Dispatchers.IO).launch {
            val schedule = scheduleRepository.getScheduleFromServer()
            if (schedule is ApiResponse.Success) {
                scheduleRepository.insertSchedule(schedule.data)
            }else {
            }
        }
    }

    private fun login(loginRequest: LoginRequest) {
        CoroutineScope(Dispatchers.IO).launch {
            delay(SPLASH_DELAY_TIME)
            val login = loginRepository.login(loginRequest)

            if (login is ApiResponse.Success) {
                Log.d(
                    TAG, "LOGIN API RESPONSE (\n" +
                            "userId: ${login.data.userId},\n" +
                            "fcmToken: ${login.data.fcmToken},\nuserId: ${login.data.userId}\n" +
                            ")"
                )
                CheckuApplication.prefs.saveUserId(login.data.userId)
            }

            val intent = Intent(baseContext, MainActivity::class.java)

            withContext(Dispatchers.Main) {

                startActivity(intent)
                finish()
            }

        }
    }


}