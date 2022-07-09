package com.example.alarmvk.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface AlarmDao {

    @Insert()
    suspend fun insertAlarm(alarmNote: AlarmNote)

    @Query("SELECT * FROM alarmnote")
    fun getAllAlarms(): LiveData<List<AlarmNote>>

    @Query("DELETE FROM alarmnote WHERE id = :id")
    suspend fun removeAlarmById(id: Int)

    @Query("SELECT * FROM alarmnote WHERE id = :id")
    suspend fun getAlarmById(id: Int): AlarmNote
}