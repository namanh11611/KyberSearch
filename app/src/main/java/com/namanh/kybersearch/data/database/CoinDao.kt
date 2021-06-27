package com.namanh.kybersearch.data.database

import androidx.room.*
import com.namanh.kybersearch.data.model.Coin

@Dao
interface CoinDao {
    @Query("SELECT * FROM coin")
    suspend fun getAll(): List<Coin>

    @Query("SELECT * FROM coin WHERE symbol LIKE :symbol LIMIT 1")
    suspend fun searchBySymbol(symbol: String): Coin

    @Query("SELECT * FROM coin WHERE symbol LIKE :text OR name LIKE :text")
    suspend fun searchBySymbolOrName(text: String): List<Coin>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(coins: List<Coin>)

    @Update
    suspend fun updateCoins(coins: List<Coin>)

    @Query("DELETE FROM coin")
    suspend fun deleteAll()

    @Delete
    suspend fun delete(coin: Coin)
}