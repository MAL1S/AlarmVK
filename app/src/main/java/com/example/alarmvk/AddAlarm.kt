package com.example.alarmvk

import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.PowerManager
import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.navigation.NavController
import com.example.alarmvk.data.AlarmNote
import com.example.alarmvk.ui.theme.AlarmVKTheme
import java.util.*


@Composable
fun AddAlarm(navController: NavController, alarmViewModel: AlarmViewModel) {
    val context = LocalContext.current

    var time by remember {
        mutableStateOf("")
    }

    var alarmHour by remember {
        mutableStateOf(0)
    }

    var alarmMinute by remember {
        mutableStateOf(0)
    }

    val calendar = Calendar.getInstance()
    val h = calendar.get(Calendar.HOUR_OF_DAY)
    val m = calendar.get(Calendar.MINUTE)

    val timePickerDialog = TimePickerDialog(
        LocalContext.current,
        { _, hour: Int, minute: Int ->
            alarmHour = hour
            alarmMinute = minute
            val minutes = if (minute < 10) "0${minute}" else "$minute"
            val hours = if (hour < 10) "0${hour}" else "$hour"
            time = "$hours:$minutes"
        }, h, m, false
    )

    AlarmVKTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    Text(
                        text = if (time == "") "Выберите время срабатывания" else "Время срабатывания: $time",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .border(0.5.dp, MaterialTheme.colors.onSurface.copy(alpha = 0.5f))
                            .clickable {
                                timePickerDialog.show()
                            }
                            .padding(16.dp)
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = {
                            val alarmNote = AlarmNote(
                                time = time,
                                dates = "",
                                hour = alarmHour,
                                minute = alarmMinute
                            )

                            alarmViewModel.insertAlarm(
                                alarmNote = alarmNote
                            )

                            val alarms = alarmViewModel.getAllAlarms().value!!

                            CustomAlarmManager.setAlarm(
                                context = context,
                                alarmNote = alarms.last()
                            )

                            navController.popBackStack()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF4361ee))
                    ) {
                        Text(text = "Добавить", color = Color.White)
                    }
                }
            }
        }
    }
}

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Log.d("ALARM", "RECEIVED")
//        val pm = context.getSystemService(Context.POWER_SERVICE) as PowerManager
//        val wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "AlarmVk:wake_lock")
//        wl.acquire(10 * 60 * 1000L /*10 minutes*/)


        val alarmNoteId = intent.extras?.getInt("alarm_id")!!
        Log.d("ALARM", alarmNoteId.toString())
        showNotification(context.applicationContext, alarmNoteId)

        Log.d("ALARM", "NOTIFIED")

        //wl.release()
    }

    private fun showNotification(context: Context, id: Int) {
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelName = "message_channel"
        val channelId = "message_id"

        //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//        val channel =
//            NotificationChannelCompat(channelId, channelName, NotificationCompat.PRIORITY_DEFAULT)
//        manager.createNotificationChannel(channel)
        //}


        val cancelIntent = Intent(context, NotificationActionService::class.java)
            .putExtra("id", id)
        cancelIntent.action = "action_cancel"
        val cancelPendingIntent = PendingIntent.getBroadcast(context, 0, cancelIntent, 0)

        val builder = NotificationCompat.Builder(context, channelId)
            .setContentTitle("Alarm")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setSmallIcon(android.R.drawable.sym_def_app_icon)
            .addAction(android.R.drawable.ic_delete, "Cancel", cancelPendingIntent)
        manager.notify(1, builder.build())
    }
}