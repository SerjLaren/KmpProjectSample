import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

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
    
    val xcf = XCFramework()
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "sharedumbrella"
            export(project(":core:settings"))
            export(project(":core:memorycache"))
            xcf.add(this)
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            api(project(":core:settings"))
            api(project(":core:memorycache"))
            implementation(project(":core:network"))
            implementation(project(":core:storage"))
        }
    }

    task("testClasses")
}

android {
    namespace = "com.serjlaren.sharedumbrella"
    compileSdk = libs.versions.compileAndroidSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.minAndroidSdk.get().toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}
