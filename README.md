# KmpProjectSample

(project is in progress now) 🛠️

## KMP sample project with **Network** and **Storage** sharing between Android and iOS.

## Stack:
- [Kotlin Multiplatform](https://kotlinlang.org/docs/multiplatform.html) - multiplatform technology
- [Ktor](https://ktor.io/) - for network
- [Multiplatform-Settings](https://github.com/russhwolf/multiplatform-settings) - to store key-value settings
- [JsonPlaceholder](https://jsonplaceholder.org/comments) - as backend api

## Structure:
- **androidApp**, **iosApp**: default KMP-project Android and iOS apps
- **core:network**: core module for network calls
- **core:storage**: core module for store key-value settings
- **shared**: default KMP module that provides **core:network** and **core:storage** classes to Android and iOS apps ([Umbrella](https://www.jetbrains.com/help/kotlin-multiplatform-dev/multiplatform-project-configuration.html#several-shared-modules))