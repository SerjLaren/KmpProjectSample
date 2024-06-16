package com.serjlaren.memorycache

import co.touchlab.stately.concurrency.Synchronizable
import co.touchlab.stately.concurrency.synchronize

// Declare this class as Singleton in your App's di/sl. Then inject anywhere and get/put in-memory stored values by keys.
class MemoryCache : Synchronizable() {

    private val memoryMap = hashMapOf<String, Any>()

    fun put(key: String, data: Any) {
        synchronize { memoryMap[key] = data }
    }

    fun containsValue(key: String) {
        return synchronize { memoryMap.contains(key) }
    }

    fun getAnyOrNull(key: String): Any? {
        return synchronize { memoryMap[key] }
    }

    fun getAny(key: String): Any {
        return synchronize { requireNotNull(memoryMap[key]) { "Value for key $key was null in MemoryCache" } }
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> getOrNull(key: String): T {
        return synchronize { memoryMap[key] as T }
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> get(key: String): T {
        return synchronize { requireNotNull(memoryMap[key] as T) { "Value for key $key was null in MemoryCache" } }
    }

    fun remove(key: String) {
        synchronize { memoryMap.remove(key) }
    }

    fun clear() {
        synchronize { memoryMap.clear() }
    }
}