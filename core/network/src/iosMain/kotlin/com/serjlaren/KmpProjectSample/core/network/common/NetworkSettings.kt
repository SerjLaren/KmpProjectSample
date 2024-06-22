@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package com.serjlaren.KmpProjectSample.core.network.common

import com.russhwolf.settings.NSUserDefaultsSettings
import com.russhwolf.settings.Settings
import platform.Foundation.NSUserDefaults

internal actual class NetworkSettings {
    internal actual val encryptedSettings: Settings = NSUserDefaultsSettings(NSUserDefaults())
}