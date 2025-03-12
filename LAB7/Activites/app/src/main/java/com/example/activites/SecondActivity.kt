package com.example.activites

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext

class SecondActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current
            ActivityScreen(
                imageRes = R.drawable.image2,
                description = "This is Activity 2",
                onNext = {
                    context.startActivity(Intent(this, ThirdActivity::class.java))
                },
                onBack = {
                    finish()
                }
            )
        }
    }
}