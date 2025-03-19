package com.example.participantmanagement

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                ParticipantApp()
            }
        }
    }
}
data class Participant(
    var name: String,
    var surname: String,
    var score: Int
)


@Composable
fun ParticipantApp() {
    var participants by remember { mutableStateOf(mutableListOf<Participant>()) }

    var name by remember { mutableStateOf("") }
    var surname by remember { mutableStateOf("") }
    var scoreText by remember { mutableStateOf("") }
    var selectedIndex by remember { mutableIntStateOf(-1) }

    var filterText by remember { mutableStateOf("") }
    var sortType by remember { mutableStateOf("None") }

    val filteredList = participants.filter {
        it.name.startsWith(filterText, ignoreCase = true) ||
                it.surname.startsWith(filterText, ignoreCase = true) ||
                (filterText.toIntOrNull()?.let { score -> it.score < score } ?: false)
    }.let {
        when (sortType) {
            "Name ↑" -> it.sortedBy { it.name }
            "Name ↓" -> it.sortedByDescending { it.name }
            "Score ↑" -> it.sortedBy { it.score }
            "Score ↓" -> it.sortedByDescending { it.score }
            else -> it
        }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Participant Manager", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(16.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Name") }, modifier = Modifier.weight(1f).padding(end = 8.dp))
            OutlinedTextField(value = surname, onValueChange = { surname = it }, label = { Text("Surname") }, modifier = Modifier.weight(1f).padding(end = 8.dp))
            OutlinedTextField(
                value = scoreText,
                onValueChange = { scoreText = it },
                label = { Text("Score") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(Modifier.height(8.dp))

        Row {
            Button(onClick = {
                val score = scoreText.toIntOrNull() ?: 0
                if (selectedIndex == -1) {
                    participants.add(Participant(name, surname, score))
                } else {
                    participants[selectedIndex] = Participant(name, surname, score)
                    selectedIndex = -1
                }
                name = ""
                surname = ""
                scoreText = ""
            }) {
                Text(if (selectedIndex == -1) "Add Participant" else "Update Participant")
            }

            Spacer(Modifier.width(8.dp))

            if (selectedIndex != -1) {
                Button(onClick = {
                    participants.removeAt(selectedIndex)
                    selectedIndex = -1
                    name = ""
                    surname = ""
                    scoreText = ""
                }) {
                    Text("Delete")
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = filterText,
            onValueChange = { filterText = it },
            label = { Text("Filter by Name/Surname or Max Score") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        Row {
            Text("Sort By:")
            Spacer(Modifier.width(8.dp))
            DropdownMenuButton(sortType) { selected ->
                sortType = selected
            }
        }

        Spacer(Modifier.height(16.dp))

        Text("Participants:", style = MaterialTheme.typography.titleMedium)

        Spacer(Modifier.height(8.dp))

        Column {
            filteredList.forEachIndexed { index, p ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clickable {
                            name = p.name
                            surname = p.surname
                            scoreText = p.score.toString()
                            selectedIndex = participants.indexOf(p)
                        },
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("${p.name} ${p.surname}", modifier = Modifier.weight(2f))
                    Text("Score: ${p.score}", modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
fun DropdownMenuButton(selected: String, onSelect: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    val options = listOf("None", "Name ↑", "Name ↓", "Score ↑", "Score ↓")

    Box {
        Button(onClick = { expanded = true }) {
            Text(selected)
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEach {
                DropdownMenuItem(text = { Text(it) }, onClick = {
                    onSelect(it)
                    expanded = false
                })
            }
        }
    }
}

