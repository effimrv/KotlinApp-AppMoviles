package com.effimrv.kotlinapp.util

fun isValidEmail(email: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

fun isNotEmpty(value: String): Boolean = value.isNotBlank()

