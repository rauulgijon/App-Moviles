plugins {
    // Plugin de la aplicación Android
    alias(libs.plugins.android.application)
    // Plugin del lenguaje Kotlin
    alias(libs.plugins.kotlin.android)
    // Plugin 'kapt' necesario para el procesador de anotaciones de Room
    id("org.jetbrains.kotlin.kapt")
}

android {
    namespace = "com.example.appf1"
    compileSdk = 36 // Versión del SDK de Android con la que se compila

    defaultConfig {
        applicationId = "com.example.appf1"
        minSdk = 26 // La versión mínima de Android que soporta esta app
        targetSdk = 36 // La versión para la que la app fue probada
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    // Opciones de compilación de Java
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    // Habilitamos ViewBinding
    // Esto nos permite acceder a los XML de forma segura (ej: binding.miBoton)
    buildFeatures {
        viewBinding = true
    }
}

// Dependencias (librerías) que usa el proyecto
dependencies {
    // Librerías base de Android
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.12.0") // Componentes Material Design
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    // ViewModel y LiveData (Para la arquitectura MVVM)
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
    implementation("androidx.activity:activity-ktx:1.9.0") // Para 'by viewModels()'

    // Room (Base de datos)
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1") // Para usar Coroutines con Room
    kapt("androidx.room:room-compiler:2.6.1") // El procesador de anotaciones

    // Carga de Imágenes (Coil)
    // Librería moderna para cargar imágenes desde URLs
    implementation("io.coil-kt:coil:2.6.0")

    // Vista de Imagen Circular (para las fotos de pilotos)
    implementation("de.hdodenhof:circleimageview:3.1.0")

    // Coroutines (Para tareas en segundo plano, como acceder a la BD)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.0")

    // RecyclerView y CardView (Para las listas)
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation("androidx.cardview:cardview:1.0.0")

    // Dependencia para el selector de idioma manual
    implementation("androidx.preference:preference-ktx:1.2.1")

    // Tests (no los usamos en este proyecto, pero son estándar)
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}