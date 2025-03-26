package com.example.safealert

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.telephony.SmsManager
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.navigation.NavController


@Composable
fun ScheduledMessageScreen(navController: NavController) {
    val context = LocalContext.current
    var message by remember { mutableStateOf("") }
    var delayTime by remember { mutableStateOf(0L) } // Timpul de întârziere în milisecunde
    var isMessageScheduled by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Setează un mesaj programat", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = message,
            onValueChange = { message = it },
            label = { Text("Mesaj") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = delayTime.toString(),
            onValueChange = { delayTime = it.toLongOrNull() ?: 0L },
            label = { Text("Timp de așteptare (în minute)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = {
            if (message.isNotEmpty() && delayTime > 0) {
                scheduleMessage(context,message, delayTime)
                isMessageScheduled = true
            }
        }) {
            Text("Programare mesaj")
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = {navController.popBackStack()}
        ) {
            Text("Inapoi")
        }

        Spacer(modifier = Modifier.height(20.dp))

        if (isMessageScheduled) {
            Text("Mesajul va fi trimis în $delayTime minute.", color = MaterialTheme.colorScheme.primary)
        }
    }
}


fun scheduleMessage(context: Context, message: String, delayInMinutes: Long) {
    val delayInMillis = delayInMinutes * 60 * 1000

    Handler(Looper.getMainLooper()).postDelayed({
        sendScheduledSMS(context, message)
        sendEmail(context, "test@example.com", "Scheduled Message", message)
    }, delayInMillis)
}


fun sendScheduledSMS(context: Context, message: String) {
    val phoneNumbers = listOf("+40786287675")

    if (ContextCompat.checkSelfPermission(context, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
        val smsManager = SmsManager.getDefault()
        phoneNumbers.forEach { smsManager.sendTextMessage(it, null, message, null, null) }

        Toast.makeText(context, "Mesajul a fost trimis!", Toast.LENGTH_LONG).show()
    } else {
        Toast.makeText(context, "Permisiune SMS lipsă!", Toast.LENGTH_LONG).show()
    }
}




