package com.example.fiszki

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.fiszki.ui.theme.FiszkiTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {


    val newScreen = NewScreen()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        setContent {
            FiszkiTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    // a tutaj wywołujesz już metodę do aplikacji
                    //dlatego jak klikniesz run to będzie Kasia a nie Róża
                    newScreen.newScreen()
                }
            }
        }
    }
}



// tutaj dodajesz elementy do layout typu Text button itd itd
@Composable
fun StartingScreen(number: Int, navController: NavController) {
    val files = IntArray(number)

    Column() {
        Text(text = "Miło Cię znów widzieć!")

        for (file in files) {
            Button(onClick = { navController.navigate("flashcards") }) {
                Text(text = file.toString())
            }
        }

        IconButton(onClick = { navController.navigate("addingFile") }) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null,
                Modifier.padding(end = 15.dp)
            )
        }
    }

}

@Composable
fun Flashcards() {

    Text(text = "flashcards")

}

data class NameValuePair(var entry: String, var def: String)

@Composable
fun AddingFile(navController: NavController) {

    val context = LocalContext.current
    val db = Firebase.firestore
    var folderName by remember {
        mutableStateOf("")
    }
    var pairs by remember { mutableStateOf(mutableListOf(NameValuePair("", ""))) }


    Column {

        Row() {
            OutlinedTextField(value = folderName,
                onValueChange = { text ->
                    folderName = text
                })

            IconButton(onClick = {
                if (folderName.isNotBlank()) {
                    pairs.forEachIndexed { index, pair ->
                        if(pair.entry.isNotBlank() && pair.def.isNotBlank()){
                        db.collection(folderName).document("pair_$index")
                            .set(pair)
                            .addOnSuccessListener {
                                Log.d(
                                    TAG,
                                    "DocumentSnapshot successfully written!"
                                )
                            }
                            .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
                    }}

                    Toast.makeText(context, "Flashcards successfully written!", Toast.LENGTH_LONG).show()
                    navController.navigate("startingScreen")

                } else {

                    Toast.makeText(context, "Folder name cannot be empty", Toast.LENGTH_LONG).show()
                }



            }) {
                Icon(
                    imageVector = Icons.Default.Done,
                    contentDescription = null
                )
            }
        }



        pairs.forEachIndexed { index, (entry, def) ->
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                var entryState by remember { mutableStateOf(entry) }
                OutlinedTextField(
                    value = entryState,
                    onValueChange = {
                        entryState = it
                        if (index == pairs.size - 1 && it.isNotBlank() && def.isNotBlank()) {
                            pairs.add(NameValuePair("", ""))
                        }

                    },
                    label = { Text("Name ${index + 1}") },
                    colors = if (entryState.isNotBlank()) {
                        TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color.Green,
                            unfocusedBorderColor = Color.Gray
                        )
                    } else {
                        TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color.Red,
                            unfocusedBorderColor = Color.Gray
                        )
                    },
                    modifier = Modifier
                        .padding(8.dp)
                        .weight(1f)
                )

                var defState by remember { mutableStateOf(def) }
                OutlinedTextField(
                    value = defState,
                    onValueChange = {
                        defState = it
                        if (index == pairs.size - 1 && it.isNotBlank() && entry.isNotBlank()) {
                            pairs.add(NameValuePair("", ""))
                        }
                    },
                    label = { Text("Definition ${index + 1}") },
                    colors = if (defState.isNotBlank()) {
                        TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color.Green,
                            unfocusedBorderColor = Color.Gray
                        )
                    } else {
                        TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color.Red,
                            unfocusedBorderColor = Color.Gray
                        )
                    },
                    modifier = Modifier
                        .padding(8.dp)
                        .weight(1f)
                )

                pairs[index] = NameValuePair(entryState, defState)

            }
        }


    }
}


// tutaj jest testowanie po prawej stronie się to zmiena, masz cały czas podgląd na to co się dzieje
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    FiszkiTheme {
        val newScreen = NewScreen()
        newScreen.newScreen()
    }
}