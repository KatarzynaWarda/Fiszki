package com.example.fiszki

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class NewScreen {
    @Composable
    fun newScreen (){
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = "startingScreen") {
            composable("startingScreen"){
                StartingScreen(number = 5,navController)
            }
            composable("addingFile"){
                AddingFile(navController)
            }
            composable("flashcards"){
                Flashcards()
            }
        }
    }
}