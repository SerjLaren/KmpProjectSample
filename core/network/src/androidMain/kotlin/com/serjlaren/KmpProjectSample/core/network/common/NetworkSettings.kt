@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package com.serjlaren.KmpProjectSample.core.network.common

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings

private const val encryptedFileName = "nw"
private const val encryptedKeyAlias = "network_master_key"

private var appContext: Context? = null

fun initNetwork(context: Context) {
    appContext = context
}

internal actual class NetworkSettings {
    internal actual val encryptedSettings: Settings = SharedPreferencesSettings(
        EncryptedSharedPreferences.create(
            encryptedFileName,
            encryptedKeyAlias,
            requireNotNull(appContext) { "Context not passed to network module (by initNetwork(context))" },
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    )
}