import org.jetbrains.kotlin.gradle.internal.Kapt3GradleSubplugin.Companion.isKaptVerbose

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.dagger.hilt.android)
}

android {
    namespace = "com.vivek.swiggy"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.vivek.swiggy"
        minSdk = 24
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    viewBinding {
        enable = true
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.0"
    }

}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.activity.ktx)

    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation (libs.kotlinx.coroutines)
    implementation (libs.gson)
    implementation (libs.androidx.activity.compose)

    //lifecycle
    implementation(libs.lifecycle.runtime)
    implementation (libs.lifecycle.viewmodel)
    implementation (libs.lifecycle.liveData)
    implementation (libs.androidx.ui.tooling.preview)

    // compose ui
    implementation (libs.androidx.ui)
    implementation (libs.androidx.material)

    // LiveData integration
    implementation (libs.androidx.runtime.livedata)

    // Compose foundation (includes LazyVerticalGrid)
    implementation (libs.androidx.foundation)
    // Image loading with Glide
    implementation (libs.glide)
    implementation (libs.compose.glide)

// Networking
    implementation(libs.squareup.retrofit)
    implementation (libs.squareup.converter)
    implementation (libs.squareup.okhttp3)

    //Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    implementation(libs.navigation.compose)


}