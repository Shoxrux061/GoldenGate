package com.golden.gate.core.cache

import android.content.Context
import android.content.SharedPreferences

class LocalStorage private constructor(context: Context) {

    private val keyIsFirst = "KEY_IS_FIRST"


    init {
        sharedPreferences = context.getSharedPreferences("news_cache", Context.MODE_PRIVATE)
    }

    companion object {
        private var appCache: LocalStorage? = null
        private var sharedPreferences: SharedPreferences? = null

        fun init(context: Context) {
            if (appCache == null) {
                appCache = LocalStorage(context)
            }
        }

        fun getObject(): LocalStorage {
            return appCache!!
        }
    }

    fun getIsFirst(): Boolean {
        return sharedPreferences!!.getBoolean(keyIsFirst, true)
    }

    fun setIsFirst() {
        sharedPreferences!!.edit().putBoolean(keyIsFirst, false).apply()
    }

}