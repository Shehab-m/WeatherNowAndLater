import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    kotlin("kapt")
    alias(libs.plugins.hilt.library)
    alias(libs.plugins.compose.compiler)
}

val localProps = Properties()
val localPropsFile = project.rootProject.file("local.properties")
if (localPropsFile.exists()) {
    localProps.load(localPropsFile.inputStream())
}

android {
    namespace = "com.vodafone.data"
    compileSdk = 34

    defaultConfig {
        minSdk = 26
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        val apiKey = localProps.getProperty("API_KEY")?.let { "\"$it\"" } ?: "\"DEFAULT_API_KEY\""
        buildConfigField("String", "API_KEY", apiKey)
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
    buildFeatures {
        buildConfig = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(project(":core"))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    testImplementation(libs.junit.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.androidx.work.runtime.ktx)
    implementation(libs.compose.runtime)
    // Hilt
    implementation(libs.hilt.android)
    implementation(libs.androidx.hilt.work)
    kapt(libs.hilt.android.compiler)
    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.gson)
    // OkHttp
    implementation(libs.okhttp)
    implementation(libs.okhttp.logger)
    // DataStore
    implementation(libs.androidx.datastore.preferences)
    // Unit Test
    testImplementation ("org.jetbrains.kotlin:kotlin-test:1.9.10")
    testImplementation ("junit:junit:4.13.2")
    testRuntimeOnly ("org.junit.jupiter:junit-jupiter-engine:5.10.0")
    // Coroutines Test
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0")
    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.0")
    // Mockito for mocking
    testImplementation ("org.mockito:mockito-core:5.5.0")
    // Robolectric
    testImplementation ("org.robolectric:robolectric:4.10")
    // Logs
    implementation ("com.jakewharton.timber:timber:5.0.1")
}
