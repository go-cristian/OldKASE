buildscript {
  repositories {
    google()
    jcenter()
    gradlePluginPortal()
  }

  dependencies {
    classpath("com.android.tools.build:gradle:4.1.3")
    classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}")
    classpath(
        "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.navigation}"
    )
    classpath("com.google.gms:google-services:4.3.5")

    // NOTE: Do not place your application dependencies here; they belong
    // in the individual module build.gradle files
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
