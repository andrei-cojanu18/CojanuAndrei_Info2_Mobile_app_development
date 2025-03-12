package com.example.activites

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext

class ThirdActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current
            ActivityScreen(
                imageRes = R.drawable.image3,
                description = "This is Activity 3",
                onNext = null,
                onBack = {
                    finish()
                }
            )
        }
    }
}