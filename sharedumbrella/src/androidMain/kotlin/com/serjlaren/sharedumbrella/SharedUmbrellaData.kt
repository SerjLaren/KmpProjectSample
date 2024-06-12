package com.serjlaren.sharedumbrella

import android.content.Context
import com.serjlaren.core.storage.initDatabase

object SharedUmbrellaData {
    fun initialize(context: Context) {
        initDatabase(context)
    }
}