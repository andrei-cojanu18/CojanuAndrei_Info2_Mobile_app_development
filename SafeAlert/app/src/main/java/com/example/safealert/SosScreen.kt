package com.example.safealert

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.telephony.SmsManager
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

@Composable
fun SosScreen(navController: NavController) {
    var isAlertActive by remember { mutableStateOf(false) }
    var backgroundColor by remember { mutableStateOf(Color(0xFFB71C1C)) }
    val context = LocalContext.current
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = if (isAlertActive) "ALERTĂ ACTIVĂ!" else "Apasă pentru SOS",
            fontSize = 24.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                isAlertActive = !isAlertActive
                backgroundColor = if (isAlertActive) Color.Red else Color.Gray

                if (isAlertActive) {
                    getCurrentLocation(context, fusedLocationClient) { location ->
                        val message = "Urgent! Am nevoie de ajutor. Locația mea este: https://maps.google.com/?q=${location.latitude},${location.longitude}"
                        val contacts = listOf("+40786287675")
                        sendSms(context, contacts, message)
                        sendEmail(context, "test@example.com", "SOS Alert", message)
                    }
                }
            },
            modifier = Modifier
                .size(150.dp)
                .clip(CircleShape),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isAlertActive) Color.Gray else Color.Red
            )
        ) {
            Text(
                text = "SOS",
                fontSize = 32.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }

        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Înapoi la Acasă")
        }
    }
}


fun getCurrentLocation(context: Context, fusedLocationClient: FusedLocationProviderClient, onLocationReceived: (Location) -> Unit) {
    if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            location?.let { onLocationReceived(it) }
        }
    } else {
        requestPermissions(context as Activity?)
    }
}

fun sendSms(context: Context, contacts: List<String>, message: String) {
    if (ContextCompat.checkSelfPermission(context, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
        val smsManager = SmsManager.getDefault()
        contacts.forEach { number ->
            smsManager.sendTextMessage(number, null, message, null, null)
        }
    } else {
        requestPermissions(context as Activity?)
    }
}

fun sendEmail(context: Context, recipient: String, subject: String, body: String) {
    val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
        data = Uri.parse("mailto:$recipient")
        putExtra(Intent.EXTRA_SUBJECT, subject)
        putExtra(Intent.EXTRA_TEXT, body)
    }
    context.startActivity(Intent.createChooser(emailIntent, "Trimite Email"))
}


fun requestPermissions(activity: Activity?) {
    if (activity == null) return

    val permissions = arrayOf(
        Manifest.permission.SEND_SMS,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    if (permissions.any {
            ActivityCompat.checkSelfPermission(activity, it) != PackageManager.PERMISSION_GRANTED
        }) {
        ActivityCompat.requestPermissions(activity, permissions, 1)
    }
}