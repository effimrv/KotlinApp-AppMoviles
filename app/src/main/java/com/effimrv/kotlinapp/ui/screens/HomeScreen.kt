package com.effimrv.kotlinapp.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.effimrv.kotlinapp.data.model.Item
import com.effimrv.kotlinapp.viewmodel.ItemViewModel
import com.effimrv.kotlinapp.viewmodel.ItemViewModelFactory

@Composable
fun HomeScreen(navController: NavController) {
    val application = LocalContext.current.applicationContext as android.app.Application
    val factory = remember { ItemViewModelFactory(application) }
    val viewModel: ItemViewModel = viewModel(factory = factory)

    val items by viewModel.items.collectAsState(initial = emptyList())
    val loading by viewModel.loading.collectAsState(initial = false)

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Button(onClick = { navController.navigate("form") }, modifier = Modifier.fillMaxWidth()) {
            Text("Nuevo")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = { navController.navigate("imagePicker") }, modifier = Modifier.fillMaxWidth()) {
            Text("Seleccionar imagen")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = { navController.navigate("camera") }, modifier = Modifier.fillMaxWidth()) {
            Text("Abrir cámara")
        }

        Spacer(modifier = Modifier.height(12.dp))

        AnimatedVisibility(visible = loading, enter = fadeIn(), exit = fadeOut()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        if (!loading) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(items) { item: Item ->
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)) {
                        Text(item.title, modifier = Modifier.weight(1f).clickable { navController.navigate("detail/${item.id}") })
                        Text("Eliminar", modifier = Modifier.clickable {
                            viewModel.delete(item)
                        })
                    }
                }
            }
        }
    }
}
