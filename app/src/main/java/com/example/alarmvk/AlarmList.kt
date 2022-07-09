package com.example.alarmvk

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.alarmvk.data.AlarmNote
import com.example.alarmvk.data.AlarmDates

@Composable
fun AlarmList(navController: NavController, alarmViewModel: AlarmViewModel) {
    val alarmList = alarmViewModel.getAllAlarms().observeAsState(arrayListOf())

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate("add")
                },
                backgroundColor = Color.Blue,
                contentColor = Color.White
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add"
                )
            }
        },
        isFloatingActionButtonDocked = true
    ) {
        LazyColumn(contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)) {
            items(alarmList.value) {
                AlarmItem(alarmNote = it)
            }
        }
    }
}

@Composable
fun AlarmItem(alarmNote: AlarmNote) {
    Row {
        Column {
            Text(text = alarmNote.time + " - ${alarmNote.id}")
            Row {
                for (k in AlarmDates.dates.keys) {
                    if (alarmNote.dates.contains("$k")) {
                        Text(
                            text = AlarmDates.dates[k]!!,
                            //fontWeight = if (alarm.dates.contains("$index")) FontWeight.Bold else FontWeight.Light,
                            modifier = Modifier
                                .padding(4.dp)
                        )
                    }
                }
            }
        }
    }
}