package com.effimrv.kotlinapp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.effimrv.kotlinapp.data.model.Item
import com.effimrv.kotlinapp.viewmodel.ItemViewModel

@Composable
fun HomeScreen(navController: NavController, viewModel: ItemViewModel = viewModel()) {
    val items by viewModel.items.collectAsState()
    val loading by viewModel.loading.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Button(onClick = { navController.navigate("form") }, modifier = Modifier.fillMaxWidth()) {
            Text("Nuevo")
        }

        Spacer(modifier = Modifier.height(12.dp))

        if (loading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(items) { item: Item ->
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)) {
                        Text(item.title, modifier = Modifier.weight(1f).clickable { navController.navigate("detail/${item.id}") })
                        Text("Eliminar", modifier = Modifier.clickable { viewModel.delete(item) })
                    }
                }
            }
        }
    }
}
