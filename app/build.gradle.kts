plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.daggerHilt)
}

android {
    namespace = "com.adm.software.posts"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.adm.software.posts"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    hilt {
        enableAggregatingTask = false
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)

    implementation(libs.hilt.android)
    annotationProcessor(libs.hilt.compiler)

    // ViewModel
    implementation(libs.lifecycle.viewmodel)
    // LiveData
    implementation(libs.lifecycle.livedata)
    // Lifecycles only (without ViewModel or LiveData)
    implementation(libs.lifecycle.runtime)

    implementation(libs.recyclerview)
    // For control over item selection of both touch and mouse driven selection
    implementation(libs.recyclerview.selection)

    implementation(libs.rxjava3.rxjava)

    implementation(libs.room.runtime)
    annotationProcessor(libs.androidx.room.compiler)
    // RxJava3 support for Room
    implementation(libs.androidx.room.rxjava3)

    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}