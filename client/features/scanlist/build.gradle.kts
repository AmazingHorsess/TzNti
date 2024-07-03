plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)
}

android {
    namespace = "com.amazinghorsess.scanlist"
    compileSdk = 34

    defaultConfig {
        minSdk = 31

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
    buildFeatures {
        compose = true
    }
    compileOptions{
        composeOptions.kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()

    }
}

dependencies {

    implementation(project(":client:domain"))
    implementation(project(":libraries:designsystem"))

    implementation(libs.kotlinx.serialization)
    implementation(libs.androidx.lifecycle.viewmodel)
    implementation(libs.androidx.corektx)
    implementation(libs.androidx.core)

    implementation(libs.kotlinx.datetime)

    implementation(libs.koin.android)
    implementation(libs.koin.compose)


    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.compose)
    implementation(libs.compose.activity)
}