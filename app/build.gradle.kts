plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

    kotlin("plugin.serialization") version "1.9.0"
    id("kotlin-kapt")
    id("androidx.room")

}

android {
    namespace = "com.tanmay.restaurant_one_banc"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.tanmay.restaurant_one_banc"
        minSdk = 24
        targetSdk = 35
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
    room {
        schemaDirectory("$projectDir/schemas")
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Retrofit and Gson
    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    // OkHttp for logging network requests (optional)
    implementation(libs.logging.interceptor)

    implementation(libs.kotlinx.serialization.json)

    implementation(libs.androidx.navigation.compose)
    implementation(libs.coil.compose)

    implementation("androidx.room:room-runtime:2.6.1") // Use latest version
    kapt("androidx.room:room-compiler:2.6.1")

    implementation("androidx.room:room-ktx:2.6.1") // Kotlin Extensions
}