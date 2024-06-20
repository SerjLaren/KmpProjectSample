plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "memorycache"
            isStatic = true
        }
    }

    sourceSets {
        all {
            languageSettings.optIn("kotlin.experimental.ExperimentalObjCRefinement")
        }
        commonMain.dependencies {
            implementation(libs.stately.concurrency)
        }
    }

    task("testClasses")
}

android {
    namespace = "com.serjlaren.memorycache"
    compileSdk = libs.versions.compileAndroidSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.minAndroidSdk.get().toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}
