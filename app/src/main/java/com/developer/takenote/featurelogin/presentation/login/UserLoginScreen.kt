package com.developer.takenote.featurelogin.presentation.login

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.developer.takenote.featurelogin.presentation.utils.LoginUiEvent
import com.developer.takenote.ui.Screen
import kotlinx.coroutines.flow.collectLatest


@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: UserLoginViewModel = hiltViewModel()
) {

    val mailState = viewModel.mailID.value
    val password = viewModel.password.value
    val scaffoldState = rememberScaffoldState()
    val offset = remember {
        mutableStateOf(0f)
    }
    val annotatedText = buildAnnotatedString {
        //append your initial text
        withStyle(
            style = SpanStyle(
                color = Color.Gray,
            )
        ) {
            append("Don't have an account? ")

        }

        // We attach this *URL* annotation to the following content
        // until `pop()` is called
        pushStringAnnotation(
            tag = "SignUp",
            annotation = "SignUp"
        )
        withStyle(
            style = SpanStyle(
                color = Color.Blue,
                fontWeight = FontWeight.Bold
            )
        ) {
            append("Sign Up")
        }

        pop()
    }

    LaunchedEffect(key1 = true) {
        viewModel.userLoginEventFlow.collectLatest { loginUiEvent ->

            when (loginUiEvent) {
                is LoginUiEvent.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(loginUiEvent.message)
                }
                is LoginUiEvent.NavigateToHome -> {
//                    scaffoldState.snackbarHostState.showSnackbar("You successfully Logged in.")
                    navController.navigate(Screen.NoteNotesScreen.route) {
                        popUpTo(0)
                    }
                }
                is LoginUiEvent.NavigateToUserSignUp -> {
                    navController.navigate(Screen.SignUpScreen.route) {
                        popUpTo(0)
                    }

                }
                else -> {
                    scaffoldState.snackbarHostState.showSnackbar("Coming soon")
                }
            }

        }
    }

    Scaffold(scaffoldState = scaffoldState) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .scrollable(
                orientation = Orientation.Vertical,
                state = rememberScrollableState { delta ->
                    offset.value = offset.value + delta
                    delta
                }
            ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {

            OutlinedTextField(
                value = mailState,
                onValueChange = {
                    viewModel.onEvent(UserLoginEvent.EnteredMail(it))
                },
                label = {
                    Text(text = "Enter MailId")
                },
                textStyle = MaterialTheme.typography.h5,
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Yellow,
                    unfocusedBorderColor = Color.White,
                    focusedLabelColor = Color.Yellow,
                    unfocusedLabelColor = Color.White
                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = {
                    viewModel.onEvent(UserLoginEvent.EnteredPassword(it))
                },
                singleLine = true,
                label = { Text(text = "Enter Password") },
                textStyle = MaterialTheme.typography.h5,
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedLabelColor = Color.Yellow,
                    focusedBorderColor = Color.Yellow,
                    unfocusedLabelColor = Color.White,
                    unfocusedBorderColor = Color.White
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    viewModel.onEvent(UserLoginEvent.UserLogin)
                },
                modifier = Modifier.padding(8.dp),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Yellow)
            ) {
                Text(text = "Login", color = Color.Black, fontSize = 17.sp)
            }

            ClickableText(
                text = annotatedText,
                onClick = { offset ->
                    // We check if there is an *URL* annotation attached to the text
                    // at the clicked position
                    annotatedText.getStringAnnotations(
                        tag = "SignUp", start = offset,
                        end = offset
                    )
                        .firstOrNull()?.let { _ ->
                            viewModel.onEvent(UserLoginEvent.SignUp)
                        }
                }
            )

        }
    }

}