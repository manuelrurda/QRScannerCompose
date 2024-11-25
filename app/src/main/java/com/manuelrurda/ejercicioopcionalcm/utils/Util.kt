package com.manuelrurda.ejercicioopcionalcm.utils

import android.util.Patterns

fun isValidEmail(email: String): Boolean {
    return email.isEmpty() || Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

fun isValidPhoneNumber(phoneNumber: String): Boolean{
    return phoneNumber.length == 10 && phoneNumber.all { c -> c.isDigit()}
}

fun getVCardString(name: String, phoneNumber: String, email: String): String{
    return "BEGIN:VCARD\nVERSION:3.0\nFN:$name\nTEL:$phoneNumber\nEMAIL:${email}\nEND:VCARD"
}

fun isValidVCardString(value: String): Boolean{
    val pattern = Regex("(?s)^BEGIN:VCARD\\RVERSION:3\\.0\\R.*END:VCARD$")
    return pattern.containsMatchIn(value)
}