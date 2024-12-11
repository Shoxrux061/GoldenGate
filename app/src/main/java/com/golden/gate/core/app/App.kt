package com.golden.gate.core.app

import android.app.Application
import com.golden.gate.core.cache.LocalStorage
import com.golden.gate.core.room.AppDataBase

class App : Application() {

    companion object {
        lateinit var instance: Application
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        AppDataBase.init(instance)
        AppDataBase.getInstance()
        LocalStorage.init(instance)
    }

}