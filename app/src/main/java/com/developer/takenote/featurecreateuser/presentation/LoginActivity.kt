package com.developer.takenote.featurecreateuser.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.developer.takenote.featurecreateuser.presentation.signup.SignUpScreen
import com.developer.takenote.featurelogin.presentation.login.LoginScreen
import com.developer.takenote.featurenote.presentation.add_edit_note.AddEditNoteScreen
import com.developer.takenote.featurenote.presentation.notes.NotesScreen
import com.developer.takenote.ui.MainViewModel
import com.developer.takenote.ui.Screen
import com.developer.takenote.ui.theme.TakeNoteTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@ExperimentalAnimationApi
@AndroidEntryPoint
class LoginActivity : ComponentActivity() {


    private val viewModel: MainViewModel by viewModels()
    private lateinit var startDestination: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        lifecycleScope.launch {

            viewModel.emailId.collectLatest {
                setContent {
                    TakeNoteTheme {
                        // A surface container using the 'background' color from the theme
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = MaterialTheme.colors.background
                        ) {
                            val navController = rememberNavController()
                            NavHost(
                                navController = navController,
                                startDestination = if (it.isNotEmpty()) {
                                    Screen.NoteNotesScreen.route + "?emailId={emailId}"
                                } else {
                                    Screen.SignInScreen.route
                                }
                            ) {
                                composable(
                                    route = Screen.SignUpScreen.route
                                ) {
                                    SignUpScreen(navController = navController)
                                }

                                composable(
                                    route = Screen.SignInScreen.route
                                ) {
                                    LoginScreen(navController = navController)
                                }

                                composable(
                                    route = Screen.NoteNotesScreen.route +
                                            "?emailId={emailId}",
                                    arguments = listOf(
                                        navArgument(name = "emailId") {
                                            type = NavType.StringType
                                            defaultValue = it
                                        }
                                    )
                                ) {
                                    NotesScreen(navController = navController)
                                }
                                composable(
                                    route = Screen.AddEditNoteNotesScreen.route +
                                            "?noteId={noteId}&noteColor={noteColor}&emailId={emailId}",
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
                                        }, navArgument(name = "emailId") {
                                            type = NavType.StringType
                                            defaultValue = it
                                        }

                                    )
                                ) {
                                    val color = it.arguments?.getInt("noteColor") ?: -1
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


    }
}

@Composable
fun Greeting2(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    TakeNoteTheme {
        Greeting2("Android")
    }
}