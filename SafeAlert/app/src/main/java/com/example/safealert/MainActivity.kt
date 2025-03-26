package com.example.safealert


import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.safealert.ui.theme.SafeAlertTheme
import com.example.safealert.SosScreen
import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.telephony.SmsManager
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat


class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            SafeAlertTheme {
                val navController = rememberNavController()
                Scaffold {
                    NavigationSetup(navController)
                    BatteryAlertScreen()
                }
            }
        }
    }
}

@Composable
fun NavigationSetup(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") { HomeScreen(navController) }
        composable("sos") { SosScreen(navController) }
        composable("monitor") { MonitorScreen(navController) }
        composable("scheduledMessage") { ScheduledMessageScreen(navController) }
    }
}

@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Safe Alert", modifier = Modifier.padding(16.dp))
        Button(onClick = { navController.navigate("sos") }) {
            Text(text = "Mergi la SOS")
        }
        Button(onClick = { navController.navigate("monitor") }) {
            Text(text = "Mergi la Monitor Screen")
        }
        Button(onClick = { navController.navigate("scheduledMessage") }) {
            Text(text = "Mergi la Scheduled Message Screen")
        }
    }
}

    @Composable
    fun BatteryAlertScreen() {
        val context = LocalContext.current

        val batteryReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent?) {
                val level = intent?.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) ?: -1
                val scale = intent?.getIntExtra(BatteryManager.EXTRA_SCALE, -1) ?: -1
                val batteryPct = (level / scale.toFloat()) * 100


                if (batteryPct <= 10) {
                    sendLowBatteryAlert(context)
                    sendEmail(context,"test@example.com", "Low Battery Alert", "Bateria dispozitivului meu este sub 10%.")

                    Toast.makeText(
                        context,
                        "Activarea modului de economisire a energiei este recomandată.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }

        val intentFilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        context.registerReceiver(batteryReceiver, intentFilter)
    }

    fun sendLowBatteryAlert(context: Context?) {
        val message = "Bateria dispozitivului meu este sub 10%. Vă rog să fiți conștienți de acest lucru."
        val phoneNumbers = listOf("+40786287675")

        if (ActivityCompat.checkSelfPermission(context!!, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
            val smsManager = SmsManager.getDefault()
            phoneNumbers.forEach { smsManager.sendTextMessage(it, null, message, null, null) }
            Toast.makeText(context, "Baterie scazuta! SMS trimis către contactele favorite!", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(context, "Permisiune SMS lipsă!", Toast.LENGTH_LONG).show()
        }
    }





