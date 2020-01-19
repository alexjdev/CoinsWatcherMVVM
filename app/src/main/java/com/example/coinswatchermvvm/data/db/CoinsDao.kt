package com.example.coinswatchermvvm.data.db

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.coinswatchermvvm.data.response.CoinResponse

@Dao
interface CoinsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @WorkerThread
    fun insertList(coinResponseList: List<CoinResponse>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(coin: CoinResponse)

    @Query("select * from coins")
    fun getCoins(): LiveData<List<CoinResponse>>

    @Query("delete from coins")
    fun deleteAllCoins()

    @Query("select count(id) from coins")
    fun getCountRecords(): Int

    @Query("select * from coins where rank = :rank LIMIT 1") //LIMIT 1 - unnecessary
    fun getCoinByRank(rank: Int): LiveData<CoinResponse>

    @Query("select * from coins where id  LIKE :id LIMIT 1") //LIMIT 1 - unnecessary
    fun getCoinById(id: String): LiveData<CoinResponse>

    @Query("select * from coins where name LIKE :name LIMIT 1") //LIMIT 1 - unnecessary
    fun getCoinByName(name: String): CoinResponse

    @Query("select * from coins where symbol LIKE :symbol LIMIT 1") //LIMIT 1 - unnecessary
    fun getCoinBySymbol(symbol: String): CoinResponse

}