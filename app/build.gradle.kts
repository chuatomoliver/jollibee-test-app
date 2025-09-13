import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("kotlin-kapt")
    id("com.google.devtools.ksp")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.certicode.jolibee_test_app"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.certicode.jolibee_test_app"
        minSdk = 33
        targetSdk = 36
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlin {
        jvmToolchain(17) // or another JDK version
        compilerOptions {
            jvmTarget = JvmTarget.fromTarget("17")
            freeCompilerArgs.add("-Xjvm-default=all")
        }
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeCompiler {
        reportsDestination = layout.buildDirectory.dir("compose_compiler")
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}
val appCompatVersion by extra("1.7.1")
val coreKtxVersion by extra("1.17.0")
val lifeCycleAndLiveDataCompilerAndViewModelKTXVersion by extra("2.9.2")
val swipeRefreshLayoutVersion by extra("1.1.0")
val activityVersion by extra("1.10.1")
val fragmentVersion by extra("1.8.9")
val retrofitVersion by extra("3.0.0")
val roomVersion by extra("2.7.2")
val coroutineVersion by extra("1.10.2")
val multidexVersion by extra("2.0.1")
val materialDesignVersion by extra("1.12.0")
val coilVersion by extra("2.7.0")
val hiltVersion by extra("2.57")
val hiltCompilerVersion by extra("1.2.0")
val composeVersion by extra("1.9.0")
val composeFoundationVersion by extra("1.9.0")
val composeMaterialVersion by extra("1.9.0")
val composeMaterial3Version by extra("1.3.2")
val composeNavigationVersion by extra("2.9.3")
val composeHiltNavigationVersion by extra("1.2.0")


dependencies {
    // architecture libarary
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.compose)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //Room Database
    implementation("androidx.room:room-runtime:$roomVersion")
    ksp("androidx.room:room-compiler:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")
    //Retrofit request
    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.squareup.retrofit2:converter-gson:$retrofitVersion")
    implementation("com.squareup.retrofit2:adapter-rxjava2:$retrofitVersion")
    //Coil load Image
    implementation("io.coil-kt:coil-compose:$coilVersion")
    //Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutineVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutineVersion")
    //Materials
    implementation("com.google.android.material:material:$materialDesignVersion")
    //Hilt
    implementation("com.google.dagger:hilt-android:$hiltVersion")
    ksp("com.google.dagger:hilt-compiler:$hiltVersion")
    ksp("androidx.hilt:hilt-compiler:$hiltCompilerVersion")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0") // Use the latest stable version
    //Multidex
    implementation("androidx.multidex:multidex:${multidexVersion}")

    // Retrofit for networking
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    // GSON converter for Retrofit
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    // OkHttp for logging
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.0")

}