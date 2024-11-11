package com.manuelrurda.ejercicioopcionalcm.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.manuelrurda.ejercicioopcionalcm.ui.theme.labelTextStyle
import com.manuelrurda.ejercicioopcionalcm.ui.theme.textFieldTextStyle

@Composable
fun LabeledTextField(
    labelText: String,
    placeholder: String,
    stateHolder: MutableState<String>,
    isValid: ((String) -> Boolean)? = null,
    keyboardOptions: KeyboardOptions? = null,
    maxLength: Int? = null,
    isOptional: Boolean? = false
) {
    var isError by remember { mutableStateOf(false) }

    Column{
        Row {
            Text(text = labelText, style = labelTextStyle)
            if (!isOptional!!){
                Text(text = "*", style = TextStyle(color = Color.Red, fontSize = 20.sp))}
        }
        Spacer(modifier = Modifier.height(5.dp))
        OutlinedTextField(
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                errorContainerColor = Color.White,
                focusedBorderColor = if (isError) Color.Red else Color.White,
            ),
            modifier = Modifier.fillMaxWidth(),
            value = stateHolder.value,
            onValueChange = { text: String ->
                stateHolder.value = if (maxLength == null || text.length <= maxLength) text
                else stateHolder.value
                if (isValid != null) {
                    isError = !isValid(text)
                }
            },
            singleLine = true,
            textStyle = textFieldTextStyle,
            placeholder = { Text(text = placeholder, style = textFieldTextStyle) },
            isError = isError,
            keyboardOptions = keyboardOptions ?: KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
        )
    }
}