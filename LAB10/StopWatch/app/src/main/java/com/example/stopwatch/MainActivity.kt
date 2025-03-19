package com.example.stopwatch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                StopwatchApp()
            }
        }
    }
}

@Composable
fun StopwatchApp() {
    var time by remember { mutableStateOf(0L) } // in milliseconds
    var isRunning by remember { mutableStateOf(false) }

    LaunchedEffect(isRunning) {
        while (isRunning) {
            delay(10L) // update every 10 ms
            time += 10
        }
    }

    val minutes = (time / 60000).toString().padStart(2, '0')
    val seconds = ((time / 1000) % 60).toString().padStart(2, '0')
    val milliseconds = ((time % 1000) / 10).toString().padStart(2, '0')

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "$minutes:$seconds:$milliseconds",
                fontSize = 48.sp,
                style = MaterialTheme.typography.headlineLarge
            )

            Spacer(modifier = Modifier.height(32.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Button(onClick = { isRunning = !isRunning }) {
                    Text(if (isRunning) "Stop" else "Start")
                }

                Button(onClick = {
                    time = 0L
                    isRunning = false
                }) {
                    Text("Reset")
                }
            }
        }
    }
}
