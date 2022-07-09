package com.example.alarmvk.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [AlarmNote::class], version = 3)
abstract class AlarmDB: RoomDatabase() {
    abstract fun getAlarmDao(): AlarmDao

    companion object {
        @Volatile
        private var instance: AlarmDB? = null

        fun getDatabase(context: Context): AlarmDB {
            val tempInstance = instance
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                var instanceDB = Room.databaseBuilder(context.applicationContext,
                    AlarmDB::class.java, "jetpack")
                    .fallbackToDestructiveMigration()
                    .build()
                instance = instanceDB
                return instanceDB
            }
        }
    }
}