package com.example.phonebook

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


class AddContactActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                AddContactForm()
            }
        }
    }
}

@Composable
fun AddContactForm() {
    var name by remember { mutableStateOf("") }
    var surname by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var linkedin by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(vertical = 100.dp)) {
        Text("Add New Contact", style = MaterialTheme.typography.headlineSmall)
        OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Name") })
        OutlinedTextField(value = surname, onValueChange = { surname = it }, label = { Text("Surname") })
        OutlinedTextField(value = phone, onValueChange = { phone = it }, label = { Text("Phone") })
        OutlinedTextField(value = address, onValueChange = { address = it }, label = { Text("Address") })
        OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email") })
        OutlinedTextField(value = linkedin, onValueChange = { linkedin = it }, label = { Text("LinkedIn") })
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            val newId = ContactRepository.contactList.size + 1
            ContactRepository.contactList.add(Contact(newId, name, surname, phone, address, email, linkedin, R.drawable.ic_person))
        }) {
            Text("Save Contact")
        }
    }
}