package com.effimrv.kotlinapp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun InputField(value: String, onValueChange: (String) -> Unit, label: String, error: String? = null) {
    Column(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(value = value, onValueChange = onValueChange, label = { Text(label) }, modifier = Modifier.fillMaxWidth())
        if (!error.isNullOrEmpty()) {
            Text(text = error)
        }
    }
}

