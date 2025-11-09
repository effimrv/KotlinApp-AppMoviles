package com.effimrv.kotlinapp.ui.screens

import android.graphics.Bitmap
import android.net.Uri
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
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun CameraScreen(navController: NavController) {
    val context = LocalContext.current
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val takePicturePreviewLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap: Bitmap? ->
        bitmap?.let {
            // guardar bitmap en cache y exponer uri
            val file = File(context.cacheDir, "${SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())}.jpg")
            FileOutputStream(file).use { out ->
                it.compress(Bitmap.CompressFormat.JPEG, 90, out)
            }
            imageUri = Uri.fromFile(file)
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Button(onClick = { takePicturePreviewLauncher.launch(null) }) {
            Text("Tomar foto (preview)")
        }

        Spacer(modifier = Modifier.height(12.dp))

        imageUri?.let { uri ->
            AsyncImage(model = uri, contentDescription = "Foto capturada", modifier = Modifier.fillMaxWidth())
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(onClick = { navController.popBackStack() }) { Text("Volver") }
    }
}
