package com.thoitietsbtybinh.sbty

import android.app.Application
import android.content.Context


class WeatherSbty : Application() {
    companion object {
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}