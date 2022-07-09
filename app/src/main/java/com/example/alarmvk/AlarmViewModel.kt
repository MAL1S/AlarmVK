package com.example.alarmvk

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.alarmvk.data.AlarmNote
import kotlinx.coroutines.launch

class AlarmViewModel(application: Application): AndroidViewModel(application) {

    private val alarmRepository = AlarmRepository(application)

    fun getAllAlarms(): LiveData<List<AlarmNote>> {
        return alarmRepository.alarms
    }

    fun insertAlarm(alarmNote: AlarmNote) {
        viewModelScope.launch {
            alarmRepository.insertAlarm(alarmNote = alarmNote)
        }
    }

    fun removeAlarmById(id: Int) {
        viewModelScope.launch {
            alarmRepository.deleteAlarmById(id = id)
        }
    }
}