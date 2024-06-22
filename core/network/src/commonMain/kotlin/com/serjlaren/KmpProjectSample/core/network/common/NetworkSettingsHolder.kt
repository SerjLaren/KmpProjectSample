package com.serjlaren.KmpProjectSample.core.network.common

import com.russhwolf.settings.get
import com.russhwolf.settings.set

private const val refreshTokenKey = "refresh_token"

internal class NetworkSettingsHolder {

    private val encryptedSettings = NetworkSettings().encryptedSettings

    var refreshToken: String
        get() {
            return encryptedSettings[refreshTokenKey] ?: ""
        }
        set(value) {
            encryptedSettings[refreshTokenKey] = value
        }
}