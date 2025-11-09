# KotlinApp-AppMoviles
LevelUpGamer: demo Android en Kotlin con Compose y persistencia local.

# LevelUpGamer‑Kotlin

Descripción breve
Aplicación Android desarrollada en Kotlin usando Jetpack Compose y arquitectura MVVM. Esta entrega parcial corresponde a la Evaluación Parcial 3 de la asignatura DSY1105 y debe evidenciar: interfaz funcional, formularios validados, navegación, gestión de estado, persistencia local (Room), acceso a recursos nativos y animaciones.

Estado del proyecto
- Versión: 0.1 — Entrega parcial
- Min SDK: 26 (Android 8.0)
- Compile/Target SDK: ajustar a la versión instalada (recomendado 33 o 34)
- Lenguaje: Kotlin
- UI: Jetpack Compose (Material 3)
- Arquitectura: MVVM
- Repositorio: https://github.com/effimrv/levelupgamer-kotlin

Requisitos (previos)
- Android Studio (última versión estable recomendada)
- JDK 11+
- Android SDK (instalar la API que uses como compileSdk)
- Emulador AVD o dispositivo físico con USB debugging
- Git configurado (user.name, user.email)
- Conexión a Internet para descargar dependencias

Estructura del proyecto
- app/
  - src/main/java/com/effimrv/levelupgamer/
    - ui/
      - screens/          -> Pantallas (HomeScreen.kt, DetailScreen.kt, etc.)
      - components/       -> Composables reutilizables (InputField.kt, Avatar.kt)
      - theme/            -> Theme, colores y tipografías
      - MainActivity.kt
    - viewmodel/
      - MainViewModel.kt
      - PlayerViewModel.kt
    - data/
      - model/            -> Entidades y DTOs (Player.kt)
      - local/
        - db/             -> AppDatabase.kt
        - dao/            -> PlayerDao.kt
      - repository/       -> PlayerRepository.kt
    - nav/
      - NavGraph.kt       -> NavHost y rutas
  - res/                 -> recursos (drawables, strings, layouts XML mínimos)
  - AndroidManifest.xml
- build.gradle.kts (project root)
- app/build.gradle.kts (module: app)
- settings.gradle.kts
- gradle/libs.versions.toml (version catalog, opcional)
- README.md
- LICENSE (MIT)
- .gitignore


Contenido esencial de app/build.gradle.kts (ejemplo mínimo)
```kotlin
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.effimrv.levelupgamerkotlin"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.effimrv.levelupgamerkotlin"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "0.1"
    }

    buildFeatures { compose = true }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.get()
    }
    kotlinOptions { jvmTarget = "1.8" }
}
dependencies {
    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.activity:activity-compose:1.7.2")
    implementation("androidx.compose.ui:ui:1.5.0")
    implementation("androidx.compose.material:material:1.5.0")
    implementation("androidx.room:room-runtime:2.5.2")
    kapt("androidx.room:room-compiler:2.5.2")
    // ... otras dependencias
}
```

Cómo ejecutar el proyecto (pasos)
1. Clonar:
```bash
git clone https://github.com/effimrv/levelupgamer-kotlin.git
cd levelupgamer-kotlin
```
2. Abrir en Android Studio: File → Open → seleccionar la carpeta del proyecto.
3. Si Android Studio pide instalar compileSdk: abrir SDK Manager e instalar la API indicada.
4. Sync Project with Gradle Files.
5. Crear AVD (Tools → AVD Manager) con API ≥ 26 o conectar dispositivo.
6. Run → Run 'app' → seleccionar AVD/dispositivo.

Comandos Git útiles
```bash
# Inicializar (si no hay repo local)
git init
git add .
git commit -m "Initial commit: skeleton and README"
git branch -M main
git remote add origin https://github.com/effimrv/levelupgamer-kotlin.git
git push -u origin main
```

Mapeo de evidencia a indicadores (resumen)
- IL2.1 (Diseño de interfaces y formularios): mostrar PlayerForm.kt + demo con entradas inválidas.
- IL2.2 (Lógica y estado): mostrar ViewModel, explicar StateFlow y modificar componente en vivo.
- IL2.3 (Persistencia y arquitectura): mostrar Room (DAO/Database) y repository; guardar/leer datos en vivo.
- IL2.4 (Recursos nativos): mostrar integración de cámara/galería y manejo de permisos.

Checklist final — verificar antes de la defensa
- [ ] El proyecto abre en Android Studio sin errores de Gradle.
- [ ] Emulador o dispositivo con API ≥ 26 listo y conectado.
- [ ] La app compila y arranca correctamente.
- [ ] Archivos clave abiertos (ver lista arriba).
- [ ] Tener marcado en el código un punto // TODO para la modificación en vivo.
- [ ] Git con commits y el repo remoto en GitHub actualizado.
- [ ] .gitignore incluye: 
  - /build/
  - /.gradle/
  - /local.properties
  - /.idea/
  - *.keystore
- [ ] Licencia (MIT) incluida si aplica.

Consejos prácticos para la demostración
- Ten los archivos que vas a modificar visibles en pestañas del editor.
- Antes de la defensa practica la modificación en vivo hasta que la puedas hacer en <2 minutos.
- Si algo falla, explica brevemente la intención, el error y el plan para corregirlo; evita perder más de 1 minuto intentando arreglar un fallo crítico.
- No necesitarás material de apoyo adicional; la defensa es práctica y técnica.

(Para API 33+ usar READ_MEDIA_IMAGES y manejar permisos en runtime.)

Contacto / Autor
- effimrv — https://github.com/effimrv

Licencia
- MIT — incluir archivo LICENSE en la raíz del repositorio.
