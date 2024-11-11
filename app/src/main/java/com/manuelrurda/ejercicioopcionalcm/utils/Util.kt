package com.manuelrurda.ejercicioopcionalcm.utils

import android.util.Patterns

fun isValidEmail(email: String): Boolean {
    return email.isEmpty() || Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

fun isValidPhoneNumber(phoneNumber: String): Boolean{
    return phoneNumber.length == 10 && phoneNumber.all { c -> c.isDigit()}
}