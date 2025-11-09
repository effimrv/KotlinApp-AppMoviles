# KotlinApp-AppMoviles

Esqueleto generado para la Evaluación Parcial 3.

Estructura clave creada:

- `app/src/main/java/com/effimrv/kotlinapp/navigation/NavGraph.kt`
- `app/src/main/java/com/effimrv/kotlinapp/ui/screens/` (LoginScreen, HomeScreen, FormScreen, DetailScreen)
- `app/src/main/java/com/effimrv/kotlinapp/ui/components/InputField.kt`
- `app/src/main/java/com/effimrv/kotlinapp/ui/theme/` (ya existente)
- `app/src/main/java/com/effimrv/kotlinapp/viewmodel/FormViewModel.kt`
- `app/src/main/java/com/effimrv/kotlinapp/data/model/Item.kt`
- `app/src/main/java/com/effimrv/kotlinapp/data/local/ItemDao.kt`
- `app/src/main/java/com/effimrv/kotlinapp/data/local/AppDatabase.kt`
- `app/src/main/java/com/effimrv/kotlinapp/data/repository/ItemRepository.kt`
- `app/src/main/java/com/effimrv/kotlinapp/di/DatabaseModule.kt`
- `app/src/main/java/com/effimrv/kotlinapp/util/Validation.kt`

Cambios en `app/build.gradle.kts`:
- Añadidas dependencias mínimas: Navigation Compose, Room (runtime/ktx), Coil, Coroutines.
- Añadido `id("kotlin-kapt")` (comenté la dependencia `kapt(...)` para evitar errores en el analizador estático; habilitar manualmente si usarás KAPT o migrar a KSP).

Cómo compilar localmente (Windows cmd.exe):

1. Abrir `Android Studio` y sincronizar Gradle (o desde el proyecto ejecutar):

   gradlew.bat --version

2. Para compilar el APK de debug desde la terminal (en la raíz del proyecto):

   .\gradlew.bat assembleDebug

Notas y siguientes pasos recomendados:

- Si vas a usar Room y quieres procesado de anotaciones, habilita KAPT en `app/build.gradle.kts` y añade la dependencia del compilador de Room (o instala y usa KSP):
  - Con KAPT: descomentar `kapt("androidx.room:room-compiler:2.8.3")` y asegurarte de tener `id("kotlin-kapt")` activo.
  - Con KSP: agregar plugin KSP y la dependencia `ksp("androidx.room:room-compiler:2.8.3")`.
- Ajusta versiones de bibliotecas si usas un `version catalog` en `gradle/libs.versions.toml` (hay aliases ya en uso en el proyecto).
- Implementa la lógica de persistencia en `ItemRepository` y conecta `FormViewModel` con la base de datos.

Si quieres, ahora puedo:
- Activar KAPT/KSP y añadir la dependencia del compilador de Room (recomiendo KSP).
- Implementar un ejemplo CRUD completo conectado a la UI.
- Añadir permisos de cámara/galería y ejemplo de carga de imagen con Coil.

Commit sugerido (ejemplo):

git add -A
git commit -m "feat: esqueleto inicial para evaluación (UI, navigation, Room skeleton, ViewModel)"

---

Si quieres que continúe y ejecute la compilación ahora, lo hago y te muestro la salida (puede tardar varios minutos).
