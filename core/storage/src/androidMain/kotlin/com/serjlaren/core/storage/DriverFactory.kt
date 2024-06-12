@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package com.serjlaren.core.storage

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver

private var appContext: Context? = null

fun initDatabase(context: Context) {
    appContext = context
}

internal actual class DriverFactory(private val context: Context) {
    internal actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(Database.Schema, context, "appdatabase.db")
    }
}

internal actual fun createDriverFactory(): DriverFactory {
    return DriverFactory(requireNotNull(appContext) { "Context not passed to database module (by initDatabase(context))" })
}