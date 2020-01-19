package com.example.coinswatchermvvm.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.coinswatchermvvm.data.response.CoinResponse


@Database(
    entities = [CoinResponse::class, SignalEntry::class],
    version = 1
)

abstract class CoinsWatcherDatabase  : RoomDatabase() {
    abstract fun coinsDao(): CoinsDao
    abstract fun signalsDao(): SignalsDao


    companion object {
        @Volatile private var instance: CoinsWatcherDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                CoinsWatcherDatabase::class.java, "coinsWatcher.db")
                .build()
    }
}