package com.effimrv.kotlinapp.ui.screens

import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage

@Composable
fun ImagePickerScreen(navController: NavController) {
    val context = LocalContext.current
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    // Modern Photo Picker (no necesita permisos en la mayoría de casos)
    val photoPickerLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        imageUri = uri
    }

    // Fallback: GetContent (en caso que quieras compatibilidad explícita)
    val getContentLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            // Usar PickVisualMedia si está disponible
            try {
                photoPickerLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            } catch (e: Exception) {
                // Fallback a GetContent
                getContentLauncher.launch("image/*")
            }
        }, modifier = Modifier.fillMaxWidth()) {
            Text("Seleccionar imagen (Photo Picker)")
        }

        Spacer(modifier = Modifier.height(12.dp))

        // También proveemos un botón de fallback explícito
        Button(onClick = { getContentLauncher.launch("image/*") }, modifier = Modifier.fillMaxWidth()) {
            Text("Seleccionar imagen (Galería - fallback)")
        }

        Spacer(modifier = Modifier.height(12.dp))

        imageUri?.let { uri ->
            AsyncImage(
                model = uri,
                contentDescription = "Imagen seleccionada",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(onClick = { navController.popBackStack() }) {
            Text("Volver")
        }
    }
}
