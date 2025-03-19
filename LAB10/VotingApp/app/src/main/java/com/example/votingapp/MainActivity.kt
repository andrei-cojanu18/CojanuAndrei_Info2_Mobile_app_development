package com.example.votingapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue       // Adaugă aceste importuri
import androidx.compose.runtime.setValue       // Adaugă aceste importuri
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.votingapp.ui.theme.VotingAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                VotingApp()
            }
        }
    }
}

data class User(val name: String, var hasVoted: Boolean = false)
data class Candidate(val name: String, var votes: Int = 0)
data class Election(val title: String, val candidates: MutableList<Candidate>)

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun VotingApp() {
    var currentUser by remember { mutableStateOf<User?>(null) }
    var isFaceVerified by remember { mutableStateOf(false) }
    var otp by remember { mutableStateOf("") }
    var otpVerified by remember { mutableStateOf(false) }

    val election = remember {
        Election("Simulare Alegeri", mutableListOf(
            Candidate("Candidat A"),
            Candidate("Candidat B"),
            Candidate("Candidat C")
        ))
    }

    Scaffold(
        modifier = Modifier.fillMaxWidth(),
        content = {
            Column(modifier = Modifier.padding(16.dp)) {
                if (currentUser == null) {
                    Text("Simulare Login:")
                    var name by remember { mutableStateOf("") }
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Nume") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true  // Forțează apelul overload-ului cu String
                    )
                    Spacer(Modifier.height(8.dp))
                    Button(
                        onClick = {
                            currentUser = User(name)
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Autentificare")
                    }
                } else if (!isFaceVerified) {
                    Text("Simulare Recunoaștere Facială")
                    Button(
                        onClick = { isFaceVerified = true },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Fața a fost recunoscută")
                    }
                } else if (!otpVerified) {
                    Text("Simulare OTP")
                    OutlinedTextField(
                        value = otp,
                        onValueChange = { otp = it },
                        label = { Text("Introdu OTP (1234)") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true  // La fel, pentru a clarifica tipul
                    )
                    Spacer(Modifier.height(8.dp))
                    Button(
                        onClick = {
                            if (otp == "1234") otpVerified = true
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Verifică OTP")
                    }
                } else if (currentUser?.hasVoted == true) {
                    Text("Ai votat deja! ", style = MaterialTheme.typography.headlineSmall)
                } else {
                    Text("Votează!", style = MaterialTheme.typography.headlineMedium)
                    Spacer(Modifier.height(8.dp))
                    election.candidates.forEach { candidate ->
                        Button(
                            onClick = {
                                candidate.votes++
                                currentUser?.hasVoted = true
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                        ) {
                            Text("Votează ${candidate.name}")
                        }
                    }
                }

                Spacer(Modifier.height(24.dp))
                Text(" Rezultate:", style = MaterialTheme.typography.titleMedium)
                election.candidates.forEach {
                    Text("${it.name}: ${it.votes} voturi")
                }
            }
        }
    )
}

