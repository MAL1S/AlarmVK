package com.example.alarmvk.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class AlarmNote(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val time: String,
    val hour: Int,
    val minute: Int,
    val dates: String
): Serializable

object AlarmDates {
    val dates = mapOf<Int, String>(
        2 to "Пн",
        3 to "Вт",
        4 to "Ср",
        5 to "Чт",
        6 to "Пт",
        7 to "Сб",
        1 to "Вс",
    )
}
