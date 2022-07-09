package com.example.alarmvk

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class NotificationActionService : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Log.d("ALARM", "RECEIVED INTERNATIONAL ${intent.action}")

        MainActivity.liveData.value = intent.extras?.getInt("id")
    }
}