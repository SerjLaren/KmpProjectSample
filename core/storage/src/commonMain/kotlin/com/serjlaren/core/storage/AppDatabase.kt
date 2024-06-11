package com.serjlaren.core.storage

internal object AppDatabase {
    internal val database = Database(createDriverFactory().createDriver())
}