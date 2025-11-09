package com.effimrv.kotlinapp.ui.screens

import android.app.Application
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.effimrv.kotlinapp.viewmodel.ItemViewModel
import androidx.compose.material3.MaterialTheme

@Composable
fun FormScreen(navController: NavController) {
    val application = LocalContext.current.applicationContext as Application
    val viewModel = remember { ItemViewModel(application) }

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var titleError by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        TextField(value = title, onValueChange = { title = it; titleError = null }, modifier = Modifier.fillMaxWidth(), label = { Text("Title") })
        Spacer(modifier = Modifier.height(8.dp))
        TextField(value = description, onValueChange = { description = it }, modifier = Modifier.fillMaxWidth(), label = { Text("Description") })
        Spacer(modifier = Modifier.height(16.dp))
        if (!titleError.isNullOrEmpty()) {
            Text(text = titleError!!, color = MaterialTheme.colorScheme.error)
            Spacer(modifier = Modifier.height(8.dp))
        }
        Button(onClick = {
            if (title.isBlank()) {
                titleError = "El título es obligatorio"
                return@Button
            }
            viewModel.insert(title = title, description = description.takeIf { it.isNotBlank() })
            navController.popBackStack()
        }, modifier = Modifier.align(Alignment.End)) {
            Text("Save")
        }
    }
}
