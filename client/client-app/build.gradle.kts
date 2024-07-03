plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)

}

android {
    namespace = "com.amazinghorsess.client_app"
    compileSdk = libs.versions.android.sdk.compile.get().toInt()


    defaultConfig {
        applicationId = "com.amazinghorsess.client_app"
        minSdk = libs.versions.android.sdk.min.get().toInt()
        targetSdk = libs.versions.android.sdk.target.get().toInt()
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(project(":libraries:designsystem"))
    implementation(project(":libraries:navigation"))
    implementation(project(":client:data:network"))
    implementation(project(":client:data:repository"))
    implementation(project(":client:domain"))
    implementation(project(":client:features:config"))
    implementation(project(":client:features:scanlist"))


    implementation(libs.ktor.client.websockets)
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.cio)


    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.compose)

    implementation(libs.compose.activity)
    implementation(libs.compose.navigation)
    implementation(libs.koin.android)
}