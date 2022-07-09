package com.example.alarmvk

import android.app.Application
import com.example.alarmvk.data.AlarmNote
import com.example.alarmvk.data.AlarmDB
import com.example.alarmvk.data.AlarmDao

class AlarmRepository(application: Application) {
    private var alarmDao: AlarmDao

    init {
        val database = AlarmDB.getDatabase(application)
        alarmDao = database.getAlarmDao()
    }

    val alarms = alarmDao.getAllAlarms()

    suspend fun insertAlarm(alarmNote: AlarmNote) {
        alarmDao.insertAlarm(alarmNote = alarmNote)
    }

    suspend fun deleteAlarmById(id: Int) {
        alarmDao.removeAlarmById(id = id)
    }
}