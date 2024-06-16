plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.sqldelight)
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
            baseName = "storage"
            isStatic = false
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(libs.sqldelight.android)
        }

        iosMain.dependencies {
            implementation(libs.sqldelight.native)
        }
    }

    task("testClasses")
}

sqldelight {
    databases {
        create("Database") {
            packageName.set("com.serjlaren.core.storage")
        }
    }
}

android {
    namespace = "com.serjlaren.core.storage"
    compileSdk = libs.versions.compileAndroidSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.minAndroidSdk.get().toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}
