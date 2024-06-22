@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package com.serjlaren.KmpProjectSample.core.network.common

import com.russhwolf.settings.Settings

internal expect class NetworkSettings() {
    internal val encryptedSettings: Settings
}