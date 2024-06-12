@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package com.serjlaren.core.storage

import app.cash.sqldelight.db.SqlDriver

internal expect class DriverFactory {
    internal fun createDriver(): SqlDriver
}

internal expect fun createDriverFactory(): DriverFactory