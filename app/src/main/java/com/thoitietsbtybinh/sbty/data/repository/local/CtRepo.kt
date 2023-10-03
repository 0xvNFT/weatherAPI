package com.thoitietsbtybinh.sbty.data.repository.local

import com.thoitietsbtybinh.sbty.data.models.Ct
import com.thoitietsbtybinh.sbty.data.models.Ctu
import com.thoitietsbtybinh.sbty.db.CityDatabase

class CtRepo(private val database: CityDatabase) {

    suspend fun searchCities(key: String) = database.getCityDao().searchCity(key)
    suspend fun updateSavedCities(obj: Ctu) = database.getCityDao().updateSavedCity(obj)
    fun getSavedCities(key: Int) = database.getCityDao().getSavedCity(key)
    suspend fun deleteSavedCities(ct: Ct) = database.getCityDao().deleteSavedCity(ct)
}