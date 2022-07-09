package com.example.alarmvk

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log

class App: Application() {

    private val broadcastReceiver = object: BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            Log.d("ALARM", "RECEIVED 2")

            when (intent?.extras?.get("actionName")) {
                "action_cancel" -> {
                    CustomAlarmManager.cancelAlarm(
                        this@App,
                        id = intent.extras!!.getInt("id"),
                        isFullyCancelled = false
                    )
                }
            }
        }

    }

    init {
        val filter = IntentFilter()
        filter.addAction("action_cancel")
        //registerReceiver(broadcastReceiver, null)
    }
}