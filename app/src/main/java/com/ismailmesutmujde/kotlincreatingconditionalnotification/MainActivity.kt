package com.ismailmesutmujde.kotlincreatingconditionalnotification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.ismailmesutmujde.kotlincreatingconditionalnotification.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var bindingMain : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingMain = ActivityMainBinding.inflate(layoutInflater)
        val view = bindingMain.root
        setContentView(view)

        bindingMain.buttonCreataNotification.setOnClickListener {
            val builder: NotificationCompat.Builder

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            val intent = Intent(this, MainActivity::class.java)

            /* You can't get notifications with this code. Use the alternative code below!!
            val goToIntent = PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT)*/

            // alternative code
            var goToIntent: PendingIntent? = null
            goToIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                PendingIntent.getActivity(this, 1,intent, PendingIntent.FLAG_MUTABLE)
            } else {
                PendingIntent.getActivity(this, 1,intent, PendingIntent.FLAG_IMMUTABLE)
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                val channelId = "channelId"
                val channelName = "channelName"
                val channelPromotion = "channelPromotion"
                val channelPriority = NotificationManager.IMPORTANCE_HIGH

                var channel : NotificationChannel? = notificationManager.getNotificationChannel(channelId)

                if (channel == null) {
                    channel = NotificationChannel(channelId, channelName, channelPriority)
                    channel.description = channelPromotion
                    notificationManager.createNotificationChannel(channel)
                }

                builder = NotificationCompat.Builder(this, channelId)
                builder.setContentTitle("Title")
                    .setContentText("Content")
                    .setSmallIcon(R.drawable.image)
                    .setContentIntent(goToIntent)
                    .setAutoCancel(true)

            } else {

                builder = NotificationCompat.Builder(this)
                builder.setContentTitle("Title")
                    .setContentText("Content")
                    .setSmallIcon(R.drawable.image)
                    .setContentIntent(goToIntent)
                    .setAutoCancel(true)
                    .priority = Notification.PRIORITY_HIGH

            }

            notificationManager.notify(1, builder.build())
        }

    }
}