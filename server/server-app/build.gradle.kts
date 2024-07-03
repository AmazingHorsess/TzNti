plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
}

android {
    namespace = "com.amazinghorsess.server_app"
    compileSdk = libs.versions.android.sdk.compile.get().toInt()


    defaultConfig {
        applicationId = "com.amazinghorsess.server_app"
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
        jvmTarget = libs.versions.jvm.target.get()
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
            excludes += "/META-INF/INDEX.LIST"
            excludes += "/META-INF/io.netty.versions.properties"
        }
    }

}

dependencies {
    implementation(project(":server:domain"))
    implementation(project(":server:data:network"))
    implementation(project(":server:data:memory"))
    implementation(project(":server:data:archiving"))
    implementation(project(":server:data:local"))
    implementation(project(":server:data:scanning"))
    implementation(project(":server:data:repository"))
    implementation(project(":server:features:main"))


    implementation(project(":libraries:designsystem"))
    implementation(project(":libraries:navigation"))


    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.compose)
    implementation(libs.compose.activity)
    implementation(libs.compose.navigation)
    implementation(libs.koin.android)
    implementation(libs.koin.compose)




}