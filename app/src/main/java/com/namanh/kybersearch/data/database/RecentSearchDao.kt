package com.namanh.kybersearch.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.namanh.kybersearch.data.model.RecentSearch

@Dao
interface RecentSearchDao {
    @Query("SELECT * FROM recent_search")
    suspend fun getAll(): List<RecentSearch>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(coins: RecentSearch)
}