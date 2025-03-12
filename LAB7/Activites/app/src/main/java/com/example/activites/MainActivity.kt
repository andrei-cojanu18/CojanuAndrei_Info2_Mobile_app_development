package com.example.activites

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current
            ActivityScreen(
                imageRes = R.drawable.image1,
                description = "This is Activity 1",
                onNext = {
                    context.startActivity(Intent(this, SecondActivity::class.java))
                },
                onBack = null
            )
        }
    }
}