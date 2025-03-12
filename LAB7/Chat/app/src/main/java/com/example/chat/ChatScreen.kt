
package com.example.chat

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(user: String, showOnlyMessage: String? = null, onSend: ((String) -> Unit)? = null) {
    var message by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("\${user}'s Chat") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (showOnlyMessage != null) {
                Card(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Message: \$showOnlyMessage",
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            } else {
                OutlinedTextField(
                    value = message,
                    onValueChange = { message = it },
                    label = { Text("Enter message") },
                    modifier = Modifier.fillMaxWidth()
                )
                Button(onClick = {
                    if (message.isNotBlank()) {
                        onSend?.invoke(message)
                    }
                }) {
                    Text("Send to User B")
                }
            }
        }
    }
}
