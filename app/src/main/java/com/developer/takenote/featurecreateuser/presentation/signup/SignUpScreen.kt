package com.developer.takenote.featurecreateuser.presentation.signup

import androidx.compose.animation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.developer.takenote.R
import com.developer.takenote.featurecreateuser.domain.model.UserAccount
import com.developer.takenote.featurecreateuser.presentation.component.DefaultCheckBox
import com.developer.takenote.featurecreateuser.presentation.util.UiEvent
import com.developer.takenote.ui.Screen
import kotlinx.coroutines.flow.collectLatest


@ExperimentalAnimationApi
@Composable
fun SignUpScreen(
    navController: NavController,
    viewModel: SignUpViewModel = hiltViewModel()
) {
    val mailState = viewModel.mailId.value
    val password = viewModel.password.value
    val isHelperCheckBoxState = viewModel.userSecurityHelperStateState.value
    val scaffoldState = rememberScaffoldState()
    val offSet = remember {
        mutableStateOf(0f)
    }
    val annotatedText = buildAnnotatedString {
        //append your initial text
        withStyle(
            style = SpanStyle(
                color = Color.Gray,
            )
        ) {
            append("Already have an account? ")

        }

        // We attach this *URL* annotation to the following content
        // until `pop()` is called
        pushStringAnnotation(
            tag = "SignIn",
            annotation = "SignIn"
        )
        withStyle(
            style = SpanStyle(
                color = Color.Blue,
                fontWeight = FontWeight.Bold
            )
        ) {
            append("Sign In")
        }

        pop()
    }

    LaunchedEffect(key1 = true) {
        viewModel.signUpEventFlow.collectLatest { signUpEvent ->

            when (signUpEvent) {
                is UiEvent.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(signUpEvent.message)
                }
                is UiEvent.SaveNewUser -> {
                    scaffoldState.snackbarHostState.showSnackbar("User Inserted Successfully")
                    navController.navigate(Screen.SignInScreen.route)
                }
                is UiEvent.LoginEvent -> {
                    navController.navigate(Screen.SignInScreen.route) {
                        popUpTo(0)
                    }
                }
            }

        }
    }

    Scaffold(scaffoldState = scaffoldState) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .scrollable(
                    orientation = Orientation.Vertical,
                    state = rememberScrollableState { delta ->
                        offSet.value = offSet.value + delta
                        delta
                    }
                ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,

            ) {
            OutlinedTextField(
                value = mailState,
                onValueChange = {
                    viewModel.onEvent(SignUpEvent.EnteredEmail(it))
                },
                label = { Text(text = "Enter Email Id") },
                textStyle = MaterialTheme.typography.h5,
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Yellow, unfocusedBorderColor = Color.White,
                    focusedLabelColor = Color.Yellow, unfocusedLabelColor = Color.White
                ),
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = password,
                onValueChange = {
                    viewModel.onEvent(SignUpEvent.EnteredPassword(it))
                },
                label = { Text(text = "Enter Password") },
                textStyle = MaterialTheme.typography.h5,
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Yellow, unfocusedBorderColor = Color.White,
                    focusedLabelColor = Color.Yellow, unfocusedLabelColor = Color.White
                ),
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
            )
            Spacer(modifier = Modifier.height(64.dp))
            Button(
                onClick = {
                    viewModel.onEvent(SignUpEvent.SignUpUser)
                },
                modifier = Modifier.padding(8.dp),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Yellow),
            ) {
                Text(text = "Sign Up", color = Color.Black, fontSize = 17.sp)
            }
            AnimatedVisibility(
                visible = !isHelperCheckBoxState.isHelperCheckBoxClicked,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(modifier = Modifier
                        .padding(24.dp)
                        .clickable {
                            viewModel.onEvent(SignUpEvent.SpinnerValueChanged(!isHelperCheckBoxState.isSpinnerExpanded))
                        }
                        .padding(8.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = isHelperCheckBoxState.helperQuestion, fontSize = 17.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            imageVector = Icons.Filled.ArrowDropDown,
                            contentDescription = "Choose one question"
                        )
                        DropdownMenu(
                            expanded = isHelperCheckBoxState.isSpinnerExpanded,
                            onDismissRequest = {
                                viewModel.onEvent(SignUpEvent.SpinnerValueChanged(false))
                            }) {
                            UserAccount.securityQuestions.forEach { item ->
                                DropdownMenuItem(onClick = {
                                    viewModel.onEvent(SignUpEvent.QuestionValueChanged(item))
                                    viewModel.onEvent(SignUpEvent.SpinnerValueChanged(false))
                                }) {
                                    Text(text = item)
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    TextField(value = isHelperCheckBoxState.helperQuestionAns, label = {
                        Text(text = "Answer the question")
                    }, onValueChange = {
                        viewModel.onEvent(SignUpEvent.QuestionAnsValueChanged(answer = it))
                    }, textStyle = MaterialTheme.typography.h5,
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color.Yellow, unfocusedBorderColor = Color.White,
                            focusedLabelColor = Color.Yellow, unfocusedLabelColor = Color.White
                        ),
                        singleLine = true
                    )
                }

            }

            Spacer(modifier = Modifier.height(8.dp))
            DefaultCheckBox(
                isChecked = isHelperCheckBoxState.isHelperCheckBoxClicked,
                label = stringResource(R.string.skip)
            ) {
                viewModel.onEvent(SignUpEvent.CheckBoxValueChanged(it))
            }
            ClickableText(
                text = annotatedText,
                onClick = { offset ->
                    // We check if there is an *URL* annotation attached to the text
                    // at the clicked position
                    annotatedText.getStringAnnotations(
                        tag = "SignIn", start = offset,
                        end = offset
                    ).firstOrNull().let { _ ->
                        viewModel.onEvent(SignUpEvent.LoginUser)
                    }
                }
            )
        }

    }


}