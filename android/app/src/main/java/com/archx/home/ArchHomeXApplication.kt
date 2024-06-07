package com.archx.home

import android.app.Application
import androidx.room.Room
import com.archx.home.data.AppContainer
import com.archx.home.data.AppDatabase
import com.archx.home.data.DefaultAppContainer
import com.archx.home.data.remote.DeviceApi

class ArchHomeXApplication: Application() {

    lateinit var container: AppContainer

    val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }

    override fun onCreate() {
        super.onCreate()

        container = DefaultAppContainer(database, DeviceApi)
    }
}