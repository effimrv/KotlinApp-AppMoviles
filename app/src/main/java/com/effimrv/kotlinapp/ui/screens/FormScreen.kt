package com.effimrv.kotlinapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.effimrv.kotlinapp.viewmodel.ItemViewModel

@Composable
fun FormScreen(navController: NavController, viewModel: ItemViewModel = viewModel()) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        TextField(value = title, onValueChange = { title = it }, modifier = Modifier.fillMaxWidth(), label = { Text("Title") })
        Spacer(modifier = Modifier.height(8.dp))
        TextField(value = description, onValueChange = { description = it }, modifier = Modifier.fillMaxWidth(), label = { Text("Description") })
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            viewModel.insert(title = title, description = description.takeIf { it.isNotBlank() })
            navController.popBackStack()
        }, modifier = Modifier.align(Alignment.End)) {
            Text("Save")
        }
    }
}
