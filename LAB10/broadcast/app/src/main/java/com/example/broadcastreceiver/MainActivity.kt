package com.example.broadcastreceiver

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.tooling.preview.Preview

public class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        BatteryStatusReceiver.register(this)

        setContent {
            MaterialTheme {
                BatteryStatusScreen()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        BatteryStatusReceiver.unregister(this)
    }
}

@Composable
fun BatteryStatusScreen() {
    val batteryLevel by BatteryStatusReceiver.batteryLevel.observeAsState()
    val isBatteryLow by BatteryStatusReceiver.batteryLow.observeAsState(initial = false)

    Surface {
        Column {
            Text(text = "Nivel baterie: ${batteryLevel ?: "necunoscut"}%", style = MaterialTheme.typography.headlineMedium)
            if (isBatteryLow == true) {
                Text(
                    text = " Bateria este scăzută!",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.headlineLarge
                )
            }
        }
    }
}



