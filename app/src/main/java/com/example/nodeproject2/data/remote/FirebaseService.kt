package com.example.nodeproject2.data.remote

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_HIGH
import android.app.PendingIntent
import android.content.Intent
import android.graphics.Color
import androidx.core.app.NotificationCompat
import com.example.nodeproject2.R
import com.example.nodeproject2.ui.MainActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import java.util.*


class FirebaseService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        println()

        val intent = Intent(applicationContext, MainActivity::class.java)
//        intent.putExtra("createdAt", "test")

        val pendingIntent = PendingIntent.getActivity(
            applicationContext,
            UUID.randomUUID().hashCode(),
            intent,
            PendingIntent.FLAG_ONE_SHOT //일회용 펜딩 인텐트
        )

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        val channel = NotificationChannel(
            resources.getString(R.string.default_notification_channel_id), //채널 ID
            "CHATTING", //채널명
            IMPORTANCE_HIGH //알림음이 울리며 헤드업 알림 표시
        )
        channel.apply {
            enableLights(true)
            lightColor = Color.RED
            enableVibration(true)
            description = "notification"
            notificationManager.createNotificationChannel(channel)
        }


        val title = "체쿠"
        val content = message.data["body"].toString()

        val notification = getNotificationBuilder(title, content, pendingIntent).build()
        notificationManager.notify(1, notification)
    }


    private fun getNotificationBuilder(
        title: String,
        content: String,
        pendingIntent: PendingIntent
    ): NotificationCompat.Builder {
        return NotificationCompat.Builder(this, "test")
            .setContentTitle(title)
            .setContentText(content)
            .setContentIntent(pendingIntent)
            .setGroupSummary(true)
            .setAutoCancel(true)
            .setSmallIcon(R.drawable.ic_checku_logo)
            .setShowWhen(true)
    }


}