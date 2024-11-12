package com.manuelrurda.ejercicioopcionalcm.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.zIndex
import com.lightspark.composeqr.QrCodeView
import com.manuelrurda.ejercicioopcionalcm.R
import com.manuelrurda.ejercicioopcionalcm.components.LabeledTextField
import com.manuelrurda.ejercicioopcionalcm.ui.theme.Blue40
import com.manuelrurda.ejercicioopcionalcm.ui.theme.HintBlack
import com.manuelrurda.ejercicioopcionalcm.ui.theme.Yellow40
import com.manuelrurda.ejercicioopcionalcm.utils.getVCardString
import com.manuelrurda.ejercicioopcionalcm.utils.isValidEmail
import com.manuelrurda.ejercicioopcionalcm.utils.isValidPhoneNumber

@Composable
fun VCardScreen (){

    val popUpExpanded = remember { mutableStateOf(false) }
    val nameState = rememberSaveable { mutableStateOf("") }
    val phoneState = rememberSaveable { mutableStateOf("") }
    val emailState = rememberSaveable { mutableStateOf("") }

    QRPopup(
        expanded = popUpExpanded,
        data = getVCardString(
            name = nameState.value,
            phoneNumber = phoneState.value,
            email = emailState.value
        )
    )

    Column(modifier = Modifier
        .fillMaxSize()
        .background(
            color = Blue40
        )
        .padding(all = 25.dp)) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center) {
            Icon(
                painter = painterResource(id = R.drawable.icon_id_card),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(300.dp))
        }
        Column(
            modifier = Modifier.fillMaxHeight().navigationBarsPadding(),
            verticalArrangement = Arrangement.SpaceEvenly) {
            LabeledTextField(
                labelText = stringResource(id = R.string.label_name),
                placeholder = stringResource(id = R.string.placeholder_name),
                stateHolder = nameState)

            LabeledTextField(
                labelText = stringResource(id = R.string.label_phone),
                placeholder = stringResource(id = R.string.placeholder_phone),
                stateHolder = phoneState,
                isValid = { phoneNumber -> isValidPhoneNumber(phoneNumber) },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.NumberPassword,
                    imeAction = ImeAction.Next
                ))

            LabeledTextField(
                labelText = stringResource(id = R.string.label_email),
                placeholder = stringResource(id = R.string.placeholder_email),
                stateHolder = emailState,
                isValid = { email -> isValidEmail(email) },
                isOptional = true,
                keyboardOptions = KeyboardOptions.Default)

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp),
                onClick = {popUpExpanded.value = true},
                colors = ButtonDefaults.buttonColors(
                    containerColor = Yellow40,
                    disabledContainerColor = HintBlack
                ),
                enabled = nameState.value.isNotEmpty() &&
                        isValidEmail(emailState.value) &&
                        isValidPhoneNumber(phoneState.value)
            ) {
                Text(
                    text = stringResource(id = R.string.button_gen_vcard),
                    color = Color.Black,
                    fontSize = 20.sp)
                Spacer(modifier = Modifier.width(15.dp))
                Icon(
                    imageVector = Icons.Default.AccountBox,
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier.size(30.dp))
            }
        }
    }
}

@Composable
fun QRPopup(expanded: MutableState<Boolean>, data: String){
    if (expanded.value) {
        println(data)
        Box(
            modifier = Modifier
                .shadow(elevation = 4.dp)
                .fillMaxSize()
                .padding(horizontal = 10.dp, vertical = 50.dp)
                .background(MaterialTheme.colorScheme.surface)
                .zIndex(10f)
                .clickable {
                    expanded.value = false
                },
            contentAlignment = Alignment.Center,
        ) {
            Popup(
                onDismissRequest = {expanded.value = false},
                alignment = Alignment.Center
            ){
                QrCodeView(
                    data = data,
                    modifier = Modifier.size(300.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun VCardPreview() {
    VCardScreen()
}