package com.example.fiszki

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.fiszki.ui.theme.FiszkiTheme

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
                    Greeting("Kasia")
                }
            }
        }
    }
}

// tutaj dodajesz elementy do layout typu Text button itd itd
@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

// tutaj jest testowanie po prawej stronie się to zmiena, masz cały czas podgląd na to co się dzieje
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    FiszkiTheme {
        Greeting("Róża")
    }
}