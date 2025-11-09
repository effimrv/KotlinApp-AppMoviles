package com.effimrv.kotlinapp.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.effimrv.kotlinapp.data.model.Item
import com.effimrv.kotlinapp.viewmodel.ItemViewModel
import com.effimrv.kotlinapp.viewmodel.ItemViewModelFactory

@Composable
fun DetailScreen(navController: NavController, itemId: String?) {
    val application = LocalContext.current.applicationContext as android.app.Application
    val factory = remember { ItemViewModelFactory(application) }
    val viewModel: ItemViewModel = viewModel(factory = factory)

    val item = produceState<Item?>(initialValue = null, key1 = itemId) {
        val idLong = itemId?.toLongOrNull()
        if (idLong != null) {
            value = viewModel.getByIdSuspend(idLong)
        }
    }

    AnimatedVisibility(visible = item.value != null, enter = fadeIn(), exit = fadeOut()) {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            Text(text = "Detalle del item: ${item.value?.title ?: "n/a"}")
            Text(text = "Descripción: ${item.value?.description ?: "Sin descripción"}", modifier = Modifier.padding(top = 8.dp))
        }
    }

    // Si item aún es null, se mostrará nada o el estado vacio; esto evita crash si id inválido
}
