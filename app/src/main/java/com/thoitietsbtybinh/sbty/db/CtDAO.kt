package com.thoitietsbtybinh.sbty.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Update
import com.thoitietsbtybinh.sbty.data.models.Ct
import com.thoitietsbtybinh.sbty.data.models.Ctu

@Dao
interface CtDAO {

    @Query("SELECT * FROM city_bd WHERE name LIKE :key || '%'")
    suspend fun searchCity(key: String): List<Ct>

    @Update(entity = Ct::class)
    suspend fun updateSavedCity(vararg obj: Ctu): Int

    @Query("SELECT * FROM city_bd WHERE isSaved= :key")
    fun getSavedCity(key: Int): LiveData<List<Ct>>

    @Delete
    suspend fun deleteSavedCity(city: Ct)
}