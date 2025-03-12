
package com.example.activites

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment

@Composable
fun ActivityScreen(
    imageRes: Int,
    description: String,
    onNext: (() -> Unit)?,
    onBack: (() -> Unit)?
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = null,
            modifier = Modifier.size(200.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(description, style = MaterialTheme.typography.headlineSmall)

        Spacer(modifier = Modifier.height(32.dp))

        Row {
            onBack?.let {
                Button(onClick = it) {
                    Text("Back")
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            onNext?.let {
                Button(onClick = it) {
                    Text("Next")
                }
            }
        }
    }
}
