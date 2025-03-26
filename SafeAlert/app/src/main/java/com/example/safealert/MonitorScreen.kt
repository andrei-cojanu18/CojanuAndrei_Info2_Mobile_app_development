package com.example.safealert

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.location.Location
import android.location.LocationManager
import android.os.Handler
import android.os.Looper
import android.telephony.SmsManager
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private var lastMovementTime = System.currentTimeMillis()
private val handler = Handler(Looper.getMainLooper())

@Composable
fun MonitorScreen(navController: NavController) {
    val context = LocalContext.current
    var isMonitoring by remember { mutableStateOf(false) }
    var timer by remember { mutableStateOf(0) }
    var accelValues by remember { mutableStateOf("X: 0.0, Y: 0.0, Z: 0.0") }
    var gyroValues by remember { mutableStateOf("X: 0.0, Y: 0.0, Z: 0.0") }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Timp Monitorizare: $timer secunde", fontSize = 24.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Accelerometru: $accelValues", fontSize = 18.sp)
        Text(text = "Giroscop: $gyroValues", fontSize = 18.sp)
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            isMonitoring = !isMonitoring
            if (isMonitoring) {
                Toast.makeText(context, "Monitorizare pornită!", Toast.LENGTH_SHORT).show()
                startMotionMonitoring(context) { accel, gyro ->
                    accelValues = accel
                    gyroValues = gyro
                }
                coroutineScope.launch {
                    while (isMonitoring) {
                        delay(1000)
                        timer++
                    }
                }
            } else {
                Toast.makeText(context, "Monitorizare oprită!", Toast.LENGTH_SHORT).show()
                timer = 0
            }
        }) {
            Text(if (isMonitoring) "Oprește Monitorizarea" else "Start Monitorizare")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { navController.popBackStack() }) {
            Text("Înapoi la Home")
        }
    }
}

fun startMotionMonitoring(context: Context, updateUI: (String, String) -> Unit) {
    val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    val gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)

    val sensorListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent?) {
            event?.let {
                val values = it.values
                val movement = values.sumOf { it.toDouble() * it.toDouble() }

                if (movement > 0.5) {
                    lastMovementTime = System.currentTimeMillis()
                }

                if (it.sensor.type == Sensor.TYPE_ACCELEROMETER) {
                    updateUI("X: ${values[0]}, Y: ${values[1]}, Z: ${values[2]}", "")
                } else if (it.sensor.type == Sensor.TYPE_GYROSCOPE) {
                    updateUI("", "X: ${values[0]}, Y: ${values[1]}, Z: ${values[2]}")
                }
            }
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
    }

    sensorManager.registerListener(sensorListener, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
    sensorManager.registerListener(sensorListener, gyroscope, SensorManager.SENSOR_DELAY_NORMAL)

    handler.postDelayed(object : Runnable {
        override fun run() {
            val currentTime = System.currentTimeMillis()
            val inactivityDuration = (currentTime - lastMovementTime) / 1000

            if (inactivityDuration >= 30) {
                sendEmergencySMS(context, inactivityDuration)
                sendEmail(context, "test@example.com", "Urgență", "Nu am mai fost activ de $inactivityDuration minute.")
            }
            handler.postDelayed(this, 1000)
        }
    }, 1000)
}

fun sendEmergencySMS(context: Context, inactivityMinutes: Long) {
    val location = getCurrentLocation(context)
    val message = "Nu am mai fost activ de $inactivityMinutes minute. Locația mea curentă este: $location"

    val phoneNumbers = listOf("+40786287675")

    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
        val smsManager = SmsManager.getDefault()
        phoneNumbers.forEach { smsManager.sendTextMessage(it, null, message, null, null) }
        Toast.makeText(context, "Mesaj de urgență trimis!", Toast.LENGTH_LONG).show()
    } else {
        Toast.makeText(context, "Permisiune SMS lipsă!", Toast.LENGTH_LONG).show()
    }
}

fun getCurrentLocation(context: Context): String {
    val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    val provider = LocationManager.GPS_PROVIDER

    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
        val location: Location? = locationManager.getLastKnownLocation(provider)
        return "https://maps.google.com/?q=${location?.latitude},${location?.longitude}"
    }
    return "Locație necunoscută"
}
