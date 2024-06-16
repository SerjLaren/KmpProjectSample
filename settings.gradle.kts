enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "KmpProjectSample"
include(":androidApp")
include(":shared")
include(":sharedumbrella")
include(":core:network")
include(":core:storage")
include(":core:settings")
include(":core:memorycache")
