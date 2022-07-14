package com.example.nodeproject2.data.remote

import com.google.firebase.messaging.FirebaseMessagingService


class FirebaseService : FirebaseMessagingService() {
    private lateinit var notiTitle: String
    private lateinit var notiBody: String

    //    private var startId = R.id.homeFragment
    private var bulletinId: Int = -1

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

//    override fun onMessageReceived(message: RemoteMessage) {
//        super.onMessageReceived(message)
//
//        when(message.data["type"].toString()) {
//            POLICY -> {
//                val category = message.data["category"].toString()
//                val region = message.data["region"].toString()
//                val title = message.data["title"].toString()
//
//                notiTitle = "$region/$category"
//                notiBody = "새로 올라온 $title 정책을 확인해보시고 혜택 받아보세요!"
//                startId = R.id.policyDetailFragment
//                bulletinId = message.data["id"]?.toInt() ?: -1
//            }
//            COMMENT -> {
//                var post = message.data["postContent"].toString()
//                if(post.length > 10) post = post.substring(10) + "..."
//                startId = R.id.communityDetailFragment
//                bulletinId = message.data["postId"]?.toInt() ?: -1
//                notiTitle = "댓글 알림"
//                notiBody = "$post 글에 댓글이 달렸어요!"
//            }
//            CHILDCOMMENT -> { }
//        }
//
//        createNotificationChannel()
//        sendNotification(notiTitle, notiBody)
//    }

//    private fun createNotificationChannel() {
//        val name = "name"
//        val descriptionText = "description"
//        val importance = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            NotificationManager.IMPORTANCE_HIGH
//        } else {
//            NotificationCompat.PRIORITY_HIGH
//        }
//        val channel = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            NotificationChannel("channel", name, importance).apply {
//                description = descriptionText
//            }
//        } else null
//
//        if(channel == null) return
//
//        val notificationManager: NotificationManager =
//            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
//            notificationManager.createNotificationChannel(channel)
//
//    }

//    private fun sendNotification(title: String, body: String) {
//        val intent = Intent(this, MainActivity::class.java)
//        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
//        intent.putExtra("startId", startId)
//        intent.putExtra("bulletinId", bulletinId)
//        val pendingIntent = PendingIntent.getActivity(this,  System.currentTimeMillis().toInt(), intent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
//
//        val builder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            NotificationCompat.Builder(this, "channel")
//        } else NotificationCompat.Builder(this)
//
//        builder
//            .setSmallIcon(R.drawable.ic_launcher_foreground)
//            .setContentTitle(title)
//            .setContentText(body)
//            .setDefaults(NotificationCompat.DEFAULT_ALL)
//            .setPriority(NotificationCompat.PRIORITY_HIGH)
//            .setContentIntent(pendingIntent)
//            .setFullScreenIntent(pendingIntent, true)
//            .setAutoCancel(true)
//
//        with(NotificationManagerCompat.from(this)) {
//            notify(bulletinId, builder.build())
//        }
//    }
}