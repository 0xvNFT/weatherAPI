package com.thoitietsbtybinh.sbty.db

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.thoitietsbtybinh.sbty.data.models.Ct
import com.thoitietsbtybinh.sbty.utils.DB_NAME


@Database(
    entities = [Ct::class],
    version = 1
)
abstract class CityDatabase : RoomDatabase() {

    abstract fun getCityDao(): CtDAO

    companion object {

        private const val TAG = "CityDatabase"

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                //do update query here,
                // works like onUpgrade method of SQLite

                Log.d(TAG, "migrate: I am here")

                //adding save table
                database.execSQL("ALTER TABLE city_bd ADD COLUMN isSaved INTEGER DEFAULT NULL")
            }

        }

        @Volatile
        private var instance: CityDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context) = Room
            .databaseBuilder(context.applicationContext, CityDatabase::class.java, DB_NAME)
            .createFromAsset(DB_NAME)
            .build()
    }
}