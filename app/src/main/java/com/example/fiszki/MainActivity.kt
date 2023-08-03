package com.example.fiszki

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.fiszki.ui.theme.FiszkiTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FiszkiTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    // a tutaj wywołujesz już metodę do aplikacji
                    //dlatego jak klikniesz run to będzie Kasia a nie Róża
                    newScreen()
                }
            }
        }
    }
}

// przejscie z ekranu 1 na 2, number to ilość przycisków z ktorych każdy będzie repersentować folder
// a ilość folderów myśle że najprościej zrobić w liście gdzie będą przechowywane nazwy
@Composable
fun newScreen (){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "startingScreen") {
        composable("startingScreen"){
            StartingScreen(number = 5,navController)
        }
        composable("addingFile"){
            AddingFile()
        }
    }
}

// tutaj dodajesz elementy do layout typu Text button itd itd
@Composable
fun StartingScreen(number: Int, navController: NavController) {
    val files = IntArray(number)

    Column () {
        Text(text = "Miło Cię znów widzieć!")


        IconButton(onClick = {  navController.navigate("addingFile") }) {

            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null)
            
        }



        for (file in files){
            Button(onClick = { navController.navigate("addingFile")}) {
                Text(text = file.toString())
            }
        }
    }

}

@Composable
fun AddingFile() {

    IconButton(onClick = { /*TODO*/ }) {
        Icon(
            imageVector = Icons.Default.Done,
            contentDescription = null)

    }


    Text(text = "Hello Android!")
}

// tutaj jest testowanie po prawej stronie się to zmiena, masz cały czas podgląd na to co się dzieje
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    FiszkiTheme {
        newScreen()
    }
}