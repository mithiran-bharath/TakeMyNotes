package com.developer.takenote.feature_note.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.developer.takenote.feature_note.presentation.add_edit_note.AddEditNoteScreen
import com.developer.takenote.feature_note.presentation.notes.NotesScreen
import com.developer.takenote.feature_note.presentation.util.Screen
import com.developer.takenote.ui.theme.TakeNoteTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TakeNoteTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                   val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = Screen.NoteScreen.route) {

                        composable(
                            route = Screen.NoteScreen.route
                        ) {
                            NotesScreen(navController = navController)
                        }
                         composable(
                            route = Screen.AddEditNoteScreen.route +
                                    "?noteid={noteId}&noteColor={noteColor}",
                             arguments = listOf(
                                 navArgument(
                                     name = "noteId"
                                 ) {
                                     type = NavType.IntType
                                     defaultValue = -1
                                 },
                                 navArgument(
                                     name = "noteColor"
                                 ) {
                                     type = NavType.IntType
                                     defaultValue = -1
                                 }

                             )
                        ) {
                             val color  = it.arguments?.getInt("noteColor") ?:-1
                            AddEditNoteScreen(
                                navController = navController,
                                noteColor = color
                            )
                        }

                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TakeNoteTheme {
        Greeting("Android")
    }
}