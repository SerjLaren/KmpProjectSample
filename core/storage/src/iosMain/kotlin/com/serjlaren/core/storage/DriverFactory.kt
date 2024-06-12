@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package com.serjlaren.core.storage

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver

internal actual class DriverFactory {
    internal actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(Database.Schema, "appdatabase.db")
    }
}

internal actual fun createDriverFactory(): DriverFactory {
    return DriverFactory()
}