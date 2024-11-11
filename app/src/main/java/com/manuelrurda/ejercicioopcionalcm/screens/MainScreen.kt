package com.manuelrurda.ejercicioopcionalcm.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.manuelrurda.ejercicioopcionalcm.R
import com.manuelrurda.ejercicioopcionalcm.VCard
import com.manuelrurda.ejercicioopcionalcm.ui.theme.Blue40
import com.manuelrurda.ejercicioopcionalcm.ui.theme.Yellow40

@Composable
fun MainScreen(navController: NavHostController) {
    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = Blue40
            )
            .padding(all = 25.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center){
        Image(
            painter = painterResource(id = R.drawable.img_qr_scan),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.weight(4f)
        )
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.5f),
            onClick = {
                      navController.navigate(VCard)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Yellow40
            )) {
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
        Spacer(modifier = Modifier.height(30.dp))
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.5f),
            onClick = { /*TODO*/ },
            colors = ButtonDefaults.buttonColors(
                containerColor = Yellow40
            )) {
            Text(
                text = stringResource(id = R.string.button_scan_qr),
                color = Color.Black,
                fontSize = 20.sp)
            Spacer(modifier = Modifier.width(15.dp))
            Icon(
                painter = painterResource(id = R.drawable.icon_qr_code),
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier.size(25.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MainPreview() {
    MainScreen(rememberNavController())
}