buildscript {
  repositories {
    google()
    jcenter()
    gradlePluginPortal()
  }

  dependencies {
    classpath("com.android.tools.build:gradle:4.2.1")
    classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}")
    classpath(
        "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.navigation}"
    )
    classpath("com.google.gms:google-services:4.3.5")
    classpath("com.vanniktech:gradle-maven-publish-plugin:0.13.0")
    classpath("org.jetbrains.dokka:dokka-gradle-plugin:1.4.10.2")
  }
}

allprojects {
  repositories {
    google()
    jcenter()
  }
}

tasks.register("clean", Delete::class) {
  delete(rootProject.buildDir)
}
