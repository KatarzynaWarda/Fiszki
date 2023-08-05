package com.example.fiszki

import android.content.ContentValues
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
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
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
//    val files = IntArray(number)

    Column() {
        Text(text = "Miło Cię znów widzieć!")
        val db = Firebase.firestore
        var fcList = listOf<String>()

//TUTAJ TRZEBA OGARNĄĆ JAK POBRAĆ LISTĘ z firebasa albo collekcji (bo to można łątwo zmienić albo documentów
        // bo teraz mamy strukturę kolekcja>dokument>kolekcja>dokument
        // i jeśli zostaiwamy w ten sposób to musimy pobrać listę pierwszych dokumentów ale jest problem, że wtedy to nie jest zwykła collection tylko collectionGroup
        // możemy zmienić żeby pierwsza kolekcja to były już nazwy folderów i wtedu już jest zwykła collection ale też nie wiem jak pobrać listę nazw z pierwszego collection
        db.collectionGroup("flashcards")
            .get()
            .addOnSuccessListener { querySnapShot: QuerySnapshot ->
                for (document in querySnapShot.documents) {
                    val docName = document.id

                    Log.d(
                        ContentValues.TAG,
                        "DocumentSnapshot successfully written!" + docName
                    )
                    fcList += docName
                }
            }

//        val files = IntArray(colRef)

        for (fcList in fcList) {
            Button(onClick = { navController.navigate("flashcards") }) {
                Text(text = fcList.toString())
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

// tutaj jest testowanie po prawej stronie się to zmiena, masz cały czas podgląd na to co się dzieje
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    FiszkiTheme {
        val newScreen = NewScreen()
        newScreen.newScreen()
    }
}