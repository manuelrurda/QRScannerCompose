package com.manuelrurda.ejercicioopcionalcm.screens

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.ContactsContract
import android.webkit.URLUtil
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
import com.manuelrurda.ejercicioopcionalcm.R
import com.manuelrurda.ejercicioopcionalcm.VCard
import com.manuelrurda.ejercicioopcionalcm.ui.theme.Blue40
import com.manuelrurda.ejercicioopcionalcm.ui.theme.Yellow40
import com.manuelrurda.ejercicioopcionalcm.utils.isValidVCardString
import ezvcard.Ezvcard

@Composable
fun MainScreen(navController: NavHostController) {

    val context = LocalContext.current
    val scanOptions = getScanOptions()

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(), onResult = {})
    val contactsPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(), onResult = {})

    val qrResult = remember { mutableStateOf("") }
    val qrLauncher = rememberLauncherForActivityResult(
        contract = ScanContract(), onResult = {result ->
            qrResult.value = result.contents
            if (qrResult.value.isNotEmpty()){
                handleResult(context, qrResult.value, contactsPermissionLauncher)
            }
        })

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = Blue40
            )
            .padding(all = 25.dp)
            .navigationBarsPadding(),
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
            onClick = {
                handlePermission(
                    context = context,
                    permission = android.Manifest.permission.CAMERA,
                    permissionLauncher = cameraPermissionLauncher,
                    onPermissionGranted = {qrLauncher.launch(scanOptions)},
                    onPermissionNeeded = {
                        Toast.makeText(
                            context,
                            context.getString(R.string.error_camera_permission_req),
                            Toast.LENGTH_SHORT).show()
                    })
            },
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

fun handleResult(
    context: Context,
    result: String,
    contactsPermissionLauncher: ManagedActivityResultLauncher<String, Boolean>
) {
    if(URLUtil.isValidUrl(result)){
        handleUrlScan(context, result)
    }else if (isValidVCardString(result)){
        handlePermission(
            context = context,
            permission = android.Manifest.permission.WRITE_CONTACTS,
            permissionLauncher = contactsPermissionLauncher,
            onPermissionGranted = {handleVCardScan(context, result)},
            onPermissionNeeded = {
                Toast.makeText(context, context.getString(R.string.error_contacts_permission_req),
                    Toast.LENGTH_SHORT).show()
            })
    }else{
        Toast.makeText(context, context.getString(R.string.error_invalid_qr),
            Toast.LENGTH_SHORT).show()
    }
}

fun handleUrlScan(context: Context, result: String) {
    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(result))
    try {
        context.startActivity(browserIntent)
    }catch (_: ActivityNotFoundException){
    }
}

fun handleVCardScan(context: Context, result: String) {
    val vCard = Ezvcard.parse(result).first()
    val name = vCard.formattedName.value
    val phone = vCard.telephoneNumbers.first().text
    val email = vCard.emails.first().value

    val contactIntent = Intent(Intent.ACTION_INSERT)
    contactIntent.setType(ContactsContract.RawContacts.CONTENT_TYPE)
    contactIntent.putExtra(ContactsContract.Intents.Insert.NAME, name)
    contactIntent.putExtra(ContactsContract.Intents.Insert.PHONE, phone)
    contactIntent.putExtra(ContactsContract.Intents.Insert.EMAIL, email)

    try {
        context.startActivity(contactIntent)
    }catch (_: ActivityNotFoundException){
    }
}

fun handlePermission(
    context: Context,
    permission: String,
    permissionLauncher: androidx.activity.result.ActivityResultLauncher<String>,
    onPermissionGranted: () -> Unit,
    onPermissionNeeded: () -> Unit
) {
    when {
        ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED -> {
            onPermissionGranted()
        }
        ActivityCompat.shouldShowRequestPermissionRationale(context as android.app.Activity, permission) -> {
            onPermissionNeeded()
        }
        else -> {
            permissionLauncher.launch(permission)
        }
    }
}

fun getScanOptions(): ScanOptions{
    val options = ScanOptions()
    options.setDesiredBarcodeFormats(ScanOptions.QR_CODE)
    options.setCameraId(0)
    options.setOrientationLocked(false)
    return options
}

@Preview(showBackground = true)
@Composable
private fun MainPreview() {
    MainScreen(rememberNavController())
}