package com.example.tictactoe.manager

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.tictactoe.R
import com.example.tictactoe.utils.Constants

class NotificationsManager(private val context: Context) {

    companion object {
        private const val TAG = "NotificationsManager"
        private const val NOTIFICATION_ID = 11471
        private const val NOTIFICATION_TITLE = "Game request from:"
    }

    fun showNotification(opponentEmail: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val channel = NotificationChannel(
                    "channelId",
                    "TicTacToe",
                    NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.description = "App notification channel."

            val notification = buildNotification(opponentEmail)
            val notificationManager = context.getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
            notificationManager.notify(NOTIFICATION_ID, notification)
        }
    }

    private fun buildNotification(opponentEmail: String): Notification {
        val intent = Intent(Constants.BROADCAST_ACTION_LISTENER)
        intent.putExtra(Constants.OPPONENT_EMAIL_KEY, opponentEmail)
        val pendingIntent = PendingIntent.getBroadcast(context, NOTIFICATION_ID, intent, PendingIntent.FLAG_CANCEL_CURRENT)
        val builder = NotificationCompat.Builder(context, "channelId")
                .setSmallIcon(R.drawable.ic_phone)
                .setContentTitle(NOTIFICATION_TITLE)
                .setContentText(opponentEmail)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
        val notification = builder.build()
        return notification
    }
}