plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.baselineprofile)
}

android {
    namespace = "com.example.baselinetest"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.baselinetest"
        minSdk = 33
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    // this works - uncomment that
//    buildTypes {
//        release {
//            isMinifyEnabled = true
//            isShrinkResources = true
//            matchingFallbacks.add("release")
//            buildConfigField(type = "String", name = "TEST_FIELD", value = "\"some_value\"")
//            proguardFiles(
//                getDefaultProguardFile("proguard-android-optimize.txt"),
//                "proguard-rules.pro"
//            )
//        }
//
//        debug {
//            isMinifyEnabled = false
//            isShrinkResources = false
//            matchingFallbacks.add("debug")
//            buildConfigField(type = "String", name = "TEST_FIELD", value = "\"some_value\"")
//        }
//    }
//
//    flavorDimensions.add("brand")
//
//    productFlavors {
//        create("flavor1") {
//            dimension = "brand"
//            isDefault = true
//            manifestPlaceholders["app_name_test"] = "my_app_name_flavor1"
//            signingConfig = signingConfigs["debug"]
//        }
//
//        create("flavor2") {
//            dimension = "brand"
//            isDefault = false
//            manifestPlaceholders["app_name_test"] = "my_app_name_flavor2"
//            signingConfig = signingConfigs["debug"]
//        }
//    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        buildConfig = true
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

// this does not work - comment that
androidComponents.finalizeDsl { extension ->
    extension.flavorDimensions.add("brand")

    extension.productFlavors.maybeCreate("flavor2").apply {
        dimension = "brand"
        isDefault = true
        manifestPlaceholders["app_name_test"] = "my_app_name_flavor2"
        signingConfig = extension.signingConfigs["debug"]
    }

    extension.productFlavors.maybeCreate("flavor1").apply {
        dimension = "brand"
        isDefault = false
        manifestPlaceholders["app_name_test"] = "my_app_name_flavor1"
        signingConfig = extension.signingConfigs["debug"]
    }

    extension.buildTypes.maybeCreate("debug").apply {
        isMinifyEnabled = true
        isShrinkResources = true
        matchingFallbacks.add("release")
        buildConfigField(type = "String", name = "TEST_FIELD", value = "\"some_value\"")
        proguardFiles(
            extension.getDefaultProguardFile("proguard-android-optimize.txt"),
            "proguard-rules.pro"
        )
    }

    extension.buildTypes.maybeCreate("release").apply {
        isMinifyEnabled = false
        isShrinkResources = false
        matchingFallbacks.add("debug")
        buildConfigField(type = "String", name = "TEST_FIELD", value = "\"some_value\"")
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
    implementation(libs.androidx.profileinstaller)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    "baselineProfile"(project(":baselineprofile"))
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
