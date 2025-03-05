package com.example.smartlight

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.smartlight.R.drawable.ic_launcher_foreground
import kotlinx.coroutines.delay
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class MainActivity : ComponentActivity() {
    private val REQUEST_CODE_POST_NOTIFICATIONS = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        NotificationHelper.createNotificationChannel(this)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    REQUEST_CODE_POST_NOTIFICATIONS
                )
            }
        }
        setContent {
            LightApp()
        }
    }
}

object NotificationHelper {

    const val CHANNEL_ID = "smart_light_channel"
    const val CHANNEL_NAME = "Smart Light Notifications"
    const val CHANNEL_DESCRIPTION = "Notifications for Smart Light app"

    fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = CHANNEL_DESCRIPTION
            }
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun sendNotification(context: Context, title: String, message: String) {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(context)) {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            notify(1, builder.build())
        }
    }
}

@Composable
fun LightApp() {
    var isLightOn by remember { mutableStateOf(false) }
    var isAutomaticMode by remember { mutableStateOf(false) }
    var backgroundColor by remember { mutableStateOf(Color.DarkGray) }
    var lightColor by remember { mutableStateOf(Color.White) }
    var currentTime by remember { mutableStateOf(LocalTime.now()) }
    val context = LocalContext.current

    LaunchedEffect(isAutomaticMode) {
        while (isAutomaticMode) {
            currentTime = LocalTime.now()
            delay(1000)
        }
    }

    if (isAutomaticMode) {
        when {
            currentTime.isAfter(LocalTime.of(7, 0)) && currentTime.isBefore(LocalTime.of(18, 0)) -> { // Correct use of LocalTime.of()
                isLightOn = true
                lightColor = Color.White
                backgroundColor = lightColor
            }

            currentTime.isAfter(LocalTime.of(18, 0)) && currentTime.isBefore(LocalTime.of(22, 0)) -> { // Correct use of LocalTime.of()
                isLightOn = true
                lightColor = Color.Yellow
                backgroundColor = lightColor
            }

            else -> {
                isLightOn = false
                backgroundColor = Color.DarkGray
            }
        }
    } else {
        backgroundColor = if (isLightOn) Color.Yellow else Color.DarkGray
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Current Time: ${currentTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"))}",
            color = if (isLightOn) Color.Black else Color.White
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            if (!isAutomaticMode) {
                isLightOn = !isLightOn
                if (isLightOn) {
                    NotificationHelper.sendNotification(context, "Light Turned On", "The light has been turned on.")
                } else {
                    NotificationHelper.sendNotification(context, "Light Turned Off", "The light has been turned off.")
                }
            }
        }) {
            Text(if (isLightOn) "Turn Off" else "Turn On")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Switch(
            checked = isAutomaticMode,
            onCheckedChange = { isAutomaticMode = it }
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Automatic Mode", color = if (isLightOn) Color.Black else Color.White)
    }
}

@Preview(showBackground = true)
@Composable
fun LightAppPreview() {
    LightApp()
}