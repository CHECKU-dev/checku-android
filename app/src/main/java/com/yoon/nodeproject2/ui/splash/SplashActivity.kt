package com.yoon.nodeproject2.ui.splash

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.firebase.messaging.FirebaseMessaging
import com.skydoves.sandwich.ApiResponse
import com.yoon.nodeproject2.BuildConfig
import com.yoon.nodeproject2.data.model.LoginRequest
import com.yoon.nodeproject2.databinding.ActivitySplashBinding
import com.yoon.nodeproject2.di.CheckuApplication
import com.yoon.nodeproject2.repository.LoginRepository
import com.yoon.nodeproject2.repository.ScheduleRepository
import com.yoon.nodeproject2.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import javax.inject.Inject


@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    lateinit var binding: ActivitySplashBinding

    @Inject
    lateinit var loginRepository: LoginRepository

    @Inject
    lateinit var scheduleRepository: ScheduleRepository

    private val TAG = "SplashActivity.class"

    private lateinit var appUpdateManager: AppUpdateManager
    private val REQUEST_CODE_UPDATE = 9999

    companion object {
        private const val SPLASH_DELAY_TIME = 1500L
    }


    //TODO 서버 통신 실패 알림 추가
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        checkUpdate() // 업데이트 검사 후 메인으로
    }

    private fun checkUpdate() {
        appUpdateManager = AppUpdateManagerFactory.create(this)
        val appUpdateInfoTask = appUpdateManager.appUpdateInfo

        appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)
            ) {
                val oldVersionCode = BuildConfig.VERSION_CODE
                val newVersionCode = appUpdateInfo.availableVersionCode()

                // 업데이트가 있으면 강제 업데이트
                if (newVersionCode > oldVersionCode) {
                    appUpdateManager.startUpdateFlowForResult(
                        appUpdateInfo,
                        AppUpdateType.IMMEDIATE,
                        this,
                        REQUEST_CODE_UPDATE
                    )
                } else {
                    start()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE_UPDATE) {
            if (resultCode != RESULT_OK) { // 업데이트 실패 또는 취소 시
                checkUpdate() // 다시 업데이트 수행
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onResume() {
        super.onResume()
        appUpdateManager
            .appUpdateInfo
            .addOnSuccessListener { appUpdateInfo ->
                // 업데이트가 중단되었다가 다시 포그라운드로 돌아왔을 때, 업데이트 진행 중이면 재개
                if (appUpdateInfo.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                    appUpdateManager.startUpdateFlowForResult(
                        appUpdateInfo,
                        AppUpdateType.IMMEDIATE,
                        this,
                        REQUEST_CODE_UPDATE
                    )
                }
            }
    }

    private fun checkNetworkConnection(): Boolean {
        val cm = baseContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true

        if (!isConnected) {
            Toast.makeText(baseContext, "네트워크 연결이 원활하지 않습니다.", Toast.LENGTH_SHORT).show()
            finish()
        }
        return isConnected
    }

    private fun start() {

        getSchedule()
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }
            // Get new FCM registration token
            val fcmToken = task.result

            fcmToken?.let {
                login(
                    LoginRequest(
                        it
                    )
                )
            }

            Log.d(TAG, "FCM: $fcmToken")
        })

    }

    private fun getSchedule() {
        CoroutineScope(Dispatchers.IO).launch {
            val schedule = scheduleRepository.getScheduleFromServer()
            if (schedule is ApiResponse.Success) {
                if (scheduleRepository.getSchedule().isEmpty()) {
                    println()
                    scheduleRepository.insertSchedule(schedule.data)
                } else {
                    scheduleRepository.updateSchedule(schedule.data)
                }
            } else {
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
            } else {

            }

            val intent = Intent(baseContext, MainActivity::class.java)

            withContext(Dispatchers.Main) {
                if (checkNetworkConnection()) {
                    startActivity(intent)
                }
                finish()
            }

        }
    }


}