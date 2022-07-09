package com.example.alarmvk

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.alarmvk.ui.theme.AlarmVKTheme

class MainActivity : ComponentActivity() {

    companion object {
        val liveData = MutableLiveData<Int>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val alarmViewModel = ViewModelProvider(this).get(AlarmViewModel::class.java)

        liveData.observe(this) {
            CustomAlarmManager.cancelAlarm(
                this,
                id = it,
                isFullyCancelled = false
            )
        }

        setContent {
            AlarmVKTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {



                    MainScreen(alarmViewModel = alarmViewModel)
                }
            }
        }
    }
}


@Composable
fun MainScreen(alarmViewModel: AlarmViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "list") {
        composable("list") {
            AlarmList(
                navController = navController,
                alarmViewModel = alarmViewModel
            )
        }
        composable("add") {
            AddAlarm(
                navController = navController,
                alarmViewModel = alarmViewModel
            )
        }
    }
}