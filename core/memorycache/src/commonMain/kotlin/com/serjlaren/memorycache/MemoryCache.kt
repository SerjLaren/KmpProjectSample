package com.serjlaren.memorycache

import co.touchlab.stately.concurrency.Synchronizable
import co.touchlab.stately.concurrency.synchronize
import kotlin.native.HiddenFromObjC

// On Swift side types of 'get' functions will be "Any?" because (link below)
// (https://github.com/kotlin-hands-on/kotlin-swift-interopedia/blob/main/docs/generics/Generic%20functions.md)
// OnSwift side you need cast "Any?" with "as! YourType"

// Declare this class as Singleton in your App's di/sl. Then inject anywhere and get/put in-memory stored values by keys.
class MemoryCache : Synchronizable() {

    private val memoryMap = hashMapOf<String, Any>()

    fun put(key: String, data: Any) {
        synchronize { memoryMap[key] = data }
    }

    fun containsValue(key: String) {
        return synchronize { memoryMap.contains(key) }
    }

    fun get(key: String): Any? {
        return synchronize { memoryMap[key] }
    }

    @HiddenFromObjC
    @Suppress("UNCHECKED_CAST")
    fun <T: Any?> getTyped(key: String): T {
        return synchronize { memoryMap[key] as T }
    }

    fun remove(key: String) {
        synchronize { memoryMap.remove(key) }
    }

    fun clear() {
        synchronize { memoryMap.clear() }
    }
}