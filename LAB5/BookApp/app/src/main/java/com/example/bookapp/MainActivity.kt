package com.example.bookapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BookApp()
        }
    }
}

@Composable
fun BookApp() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "home") {
        composable("home") { HomeScreen(navController) }
        composable("chapter1") { ChapterScreen(navController, "Chapter 1", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla facilisi. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas.",R.drawable.chapter1) }
        composable("chapter2") { ChapterScreen(navController, "Chapter 2", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla facilisi. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla facilisi. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas.", R.drawable.chapter2) }
        composable("chapter3") { ChapterScreen(navController, "Chapter 3", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla facilisi. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla facilisi. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas.", R.drawable.chapter3)}
        composable("chapter4") { ChapterScreen(navController, "Chapter 4", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla facilisi. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas.",R.drawable.chapter4) }
    }
}

@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(painter = painterResource(id = R.drawable.home),
            contentDescription = "Home",
            modifier = Modifier.height(200.dp))
        Text("Welcome to the Book App", fontSize = 24.sp)
        Spacer(modifier = Modifier.height(20.dp))
        Text("Select a chapter below:", fontSize = 18.sp)
        Spacer(modifier = Modifier.height(10.dp))

        val chapters = listOf("chapter1" to "Chapter 1", "chapter2" to "Chapter 2", "chapter3" to "Chapter 3", "chapter4" to "Chapter 4")
        chapters.forEach { (route, title) ->
            Button(onClick = { navController.navigate(route) },
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(title, fontSize = 18.sp)
            }
        }
    }
}

@Composable
fun ChapterScreen(navController: NavController, title: String, content: String, imageRes: Int) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(title, fontSize = 24.sp)
        Spacer(modifier = Modifier.height(10.dp))
        Image(painter = painterResource(id = imageRes),
            contentDescription = title,
            modifier = Modifier.height(200.dp))
        Spacer(modifier = Modifier.height(10.dp))
        Text(content, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = { navController.popBackStack() }, shape = RoundedCornerShape(8.dp)) {
            Text("Back to Home")
        }
    }
}
