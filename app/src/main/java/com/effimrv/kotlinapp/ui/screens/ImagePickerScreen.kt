package com.effimrv.kotlinapp.ui.screens

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
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

    // Launcher para seleccionar imagen
    val pickLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        imageUri = uri
    }

    // Permiso para leer imágenes (Android 13+ usa READ_MEDIA_IMAGES). Usamos una única request por simplicidad.
    val permission = android.Manifest.permission.READ_EXTERNAL_STORAGE

    var hasPermission by remember { mutableStateOf(true) }

    val permLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
        hasPermission = granted
        if (granted) {
            pickLauncher.launch("image/*")
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            // Pedir permiso y luego abrir selector
            permLauncher.launch(permission)
        }, modifier = Modifier.fillMaxWidth()) {
            Text("Seleccionar imagen desde galería")
        }

        Spacer(modifier = Modifier.height(12.dp))

        if (!hasPermission) {
            Text("Permiso denegado. Habilítalo en ajustes para seleccionar imágenes.")
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = {
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = Uri.fromParts("package", context.packageName, null)
                }
                context.startActivity(intent)
            }) {
                Text("Abrir ajustes")
            }
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
