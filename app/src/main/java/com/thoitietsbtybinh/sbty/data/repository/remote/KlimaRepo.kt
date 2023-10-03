package com.thoitietsbtybinh.sbty.data.repository.remote

import com.thoitietsbtybinh.sbty.network.FitoRetoCL

class KlimaRepo {

    suspend fun getWeatherByLocation(lat: String, lon: String) =
        FitoRetoCL.api.getWeatherByLocation(lat, lon)

    suspend fun getWeatherByCityID(id: String) = FitoRetoCL.api.getWeatherByCityID(id)
    suspend fun getWeatherForecast(lat: String, lon: String, exclude: String) =
        FitoRetoCL.api.getWeatherForecast(lat, lon, exclude)
}