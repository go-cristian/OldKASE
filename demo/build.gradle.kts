plugins {
  id("com.android.application")
  kotlin("android")
  kotlin("kapt")
}

android {
  compileSdkVersion(Versions.baseSdk)
  defaultConfig {
    minSdkVersion(Versions.minSdk)
    targetSdkVersion(Versions.baseSdk)
    versionCode = 1
    versionName = "1.0"
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }
  buildTypes {
    getByName("release") {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
    }
  }
  viewBinding {
    android.buildFeatures.viewBinding = true
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
  }
  // For Kotlin projects
  kotlinOptions {
    jvmTarget = "1.8"
  }
}

dependencies {
  compileOnly( project(":annotations"))
  kapt( project(":annotation-processor"))

  implementation("org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}")
  implementation("androidx.core:core-ktx:${Versions.ktx}")
  implementation("androidx.appcompat:appcompat:1.2.0")
  implementation("com.google.android.material:material:${Versions.materialDesign}")
  implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
  implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.1")
}