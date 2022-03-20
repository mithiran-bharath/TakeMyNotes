package com.developer.takenote.featurecreateuser.presentation.component


import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation


@Composable
fun DefaultLabeledTextFiled(
    text: String,
    label: String,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
    textStyle: TextStyle,
    singleLine: Boolean,
    onFocusChange: (FocusState) -> Unit,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    
) {

    OutlinedTextField(
        value = text,
        onValueChange = onValueChange,
        singleLine = singleLine,
        modifier = modifier.onFocusChanged {
            onFocusChange(it)
        },
        label = {
            Text(text = label)
        },
        textStyle = textStyle,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions
    )
}