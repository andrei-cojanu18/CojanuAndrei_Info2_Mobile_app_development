package com.example.phonebook

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp


class DetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val contactId = intent.getIntExtra("contactId", -1)
        val contact = ContactRepository.contactList.find { it.id == contactId }

        setContent {
            MaterialTheme {
                if (contact != null) {
                    ContactDetailScreen(contact)
                } else {
                    Surface(
                        modifier = Modifier.fillMaxSize()
                            .padding(vertical = 24.dp),
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize().padding(16.dp),
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Contact not found.",
                                style = MaterialTheme.typography.headlineSmall
                            )
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun ContactDetailScreen(contact: Contact) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 100.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = contact.imageRes),
                contentDescription = null,
                modifier = Modifier.size(100.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                "${contact.name} ${contact.surname}",
                style = MaterialTheme.typography.headlineSmall
            )
            Text("Phone: ${contact.phone}")
            Text("Email: ${contact.email}")
            Text("Address: ${contact.address}")
            Text("LinkedIn: ${contact.linkedin}")
        }
    }
}
