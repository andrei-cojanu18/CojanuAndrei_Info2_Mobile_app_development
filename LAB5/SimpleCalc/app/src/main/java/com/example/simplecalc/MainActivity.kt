package com.example.simplecalc

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.simplecalc.ui.theme.SimpleCalcTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SimpleCalcTheme {
                var text1 by remember { mutableStateOf("") }
                var text2 by remember { mutableStateOf("") }
                var result by remember { mutableStateOf("0") }

                Surface(
                    modifier = Modifier.fillMaxSize()
                        .windowInsetsPadding(WindowInsets.safeDrawing)
                ) {
                    Column() {
                        TextInput(text1,text2,onValue1Change = {text1 = it}, onValue2Change = {text2 = it})
                        Spacer(modifier = Modifier.height(50.dp))
                        Buttons(text1,text2,onResultChange = {result = it})
                        Spacer(modifier = Modifier.height(50.dp))
                        resultText(result.toInt())
                    }
                }


                }
            }
        }
    }


@Composable
fun TextInput(text1: String,
              text2: String,
              onValue1Change: (String) -> Unit,
              onValue2Change: (String) -> Unit) {

    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    )
    {
        OutlinedTextField(
            value = text1,
            onValueChange = onValue1Change,
            label = { Text("Introdu un numar") },
            modifier = Modifier.padding(horizontal = 16.dp)
                .fillMaxWidth()
        )

        OutlinedTextField(
            value = text2,
            onValueChange = onValue2Change,
            label = { Text("Introdu un numar") },
            modifier = Modifier.padding(horizontal = 16.dp)
                .fillMaxWidth()
        )
    }
}

@Composable
fun Buttons(text1: String, text2: String,onResultChange: (String) -> Unit){
    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(horizontal = 16.dp)

    ){
        Button(
            onClick = {val sum = adunare(text1, text2)
                onResultChange(sum)},

            modifier = Modifier.weight(0.3f)
                .height(48.dp)
        ) {
            Text("+")

        }

        Button(
            onClick = {val sum = inmultire(text1, text2)
                onResultChange(sum)},
            modifier = Modifier.weight(0.3f)
                .height(48.dp)
        ) {
            Text("*")
        }
    }
    Spacer(modifier = Modifier.height(16.dp))
    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(horizontal = 16.dp)

    ){
        Button(
            onClick = {val sum = scadere(text1, text2)
                onResultChange(sum)},
            modifier = Modifier.weight(0.3f)
                .height(48.dp)
        ) {
            Text("-")

        }

        Button(
            onClick = {val sum = impartire(text1, text2)
                onResultChange(sum)},
            modifier = Modifier.weight(0.3f)
                .height(48.dp)
        ) {
            Text("/")
        }
    }
}



fun adunare(text1: String, text2: String): String {
    var result : Int
    result = text1.toInt() + text2.toInt()
    return result.toString()
}

fun scadere(text1: String, text2: String): String {
    var result : Int
    result = text1.toInt() - text2.toInt()
    return result.toString()
}

fun inmultire(text1: String, text2: String): String {
    var result : Int
    result = text1.toInt() * text2.toInt()
    return result.toString()
}

fun impartire(text1: String, text2: String): String {
    var result : Int
    result = text1.toInt() / text2.toInt()
    return result.toString()
}

@Composable
fun resultText(result: Int){
    Text(text = result.toString(),
        textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth(),
        style = TextStyle(
            color = Color.Black,
            fontSize = 200.sp
    )

    )
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SimpleCalcTheme {
        var text1 by remember { mutableStateOf("") }
        var text2 by remember { mutableStateOf("") }
        var result by remember { mutableStateOf("") }

        Surface(
            modifier = Modifier.fillMaxSize()
                .windowInsetsPadding(WindowInsets.safeDrawing)
        ) {
            Column() {
                TextInput(text1,text2,onValue1Change = {text1 = it}, onValue2Change = {text2 = it})
                Spacer(modifier = Modifier.height(50.dp))
                Buttons(text1,text2,onResultChange = {result = it})
                Spacer(modifier = Modifier.height(50.dp))
                resultText(0)
            }
        }

    }
}