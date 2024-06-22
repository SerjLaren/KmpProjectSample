package com.serjlaren.sharedumbrella

import android.content.Context
import com.serjlaren.KmpProjectSample.core.network.common.initNetwork
import com.serjlaren.core.storage.initDatabase

// Call 'initialize' function in your Android's Application's or SingleActivity's 'onCreate' function to initialize shared data
object SharedUmbrellaData {
    fun initialize(context: Context) {
        initDatabase(context)
        initNetwork(context)
    }
}