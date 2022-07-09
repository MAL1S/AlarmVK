package com.example.alarmvk

import android.app.AlarmManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.core.content.getSystemService
import com.example.alarmvk.data.AlarmNote
import java.util.*

object CustomAlarmManager {

    fun setAlarm(context: Context, alarmNote: AlarmNote) {
        //for (ch in alarmNote.dates) {
        //val day = ch.toString().toInt()
        val calendar = Calendar.getInstance()

//            calendar.set(Calendar.YEAR, Calendar.WEEK_OF_MONTH+1)
//            calendar.set(Calendar.DAY_OF_WEEK, day)
        if (alarmNote.hour < calendar.get(Calendar.HOUR_OF_DAY)
            || alarmNote.hour < calendar.get(Calendar.HOUR_OF_DAY)
            && alarmNote.minute < calendar.get(Calendar.MINUTE)
        ) {
            calendar.set(Calendar.DAY_OF_YEAR, (Calendar.DAY_OF_YEAR + 7) % 365)
        }

        calendar.set(Calendar.HOUR_OF_DAY, alarmNote.hour)
        calendar.set(Calendar.MINUTE, alarmNote.minute)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
            .putExtra("alarm_id", alarmNote.id)
        Log.d("ALARM", "${alarmNote.id}")
        //intent.putExtra("alarm_id", alarmNote.id)
        val pendingIntent = PendingIntent.getBroadcast(context, alarmNote.id, intent, 0)

//            alarmManager.setRepeating(
//                AlarmManager.RTC_WAKEUP,
//                //calendar.timeInMillis,
//                Calendar.getInstance().timeInMillis + 2000,
//                1000,
//                pendingIntent
//            )
        val millis: Long = if (alarmNote.hour < calendar.get(Calendar.HOUR_OF_DAY)
            || alarmNote.hour < calendar.get(Calendar.HOUR_OF_DAY)
            && alarmNote.minute < calendar.get(Calendar.MINUTE)) {
            1000 * 60 * 60 * 24 * 7
        } else {
            0
        }
        alarmManager.set(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis + millis,
            pendingIntent
        )

        Log.d("ALARM", "SENT")
        //}
    }

    fun cancelAlarm(context: Context, id: Int, isFullyCancelled: Boolean) {
        Log.d("ALARM", "CANCELLED $id")
        val intent = Intent(context, AlarmNote::class.java)
        val sender = PendingIntent.getBroadcast(context, id, intent, 0)
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(sender)
        Log.d("ALARM", "${alarmManager.nextAlarmClock}")
        val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        nm.cancelAll()

        if (isFullyCancelled) {

        }
    }
}