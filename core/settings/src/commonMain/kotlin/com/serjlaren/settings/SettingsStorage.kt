package com.serjlaren.settings

import com.russhwolf.settings.Settings
import com.russhwolf.settings.long

private const val KEY_LAST_RESTART_TIMESTAMP = "last_start_timestamp"

class SettingsStorage {

    private val settings = Settings()

    var lastStartTimestamp by settings.long(KEY_LAST_RESTART_TIMESTAMP, 0L)
}