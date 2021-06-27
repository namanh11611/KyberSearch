package com.namanh.kybersearch.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.namanh.kybersearch.data.model.Coin
import com.namanh.kybersearch.data.model.RecentSearch
import com.namanh.kybersearch.utils.AppUtils

@Database(entities = [Coin::class, RecentSearch::class], version = AppUtils.DATABASE_VERSION, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun coinDao(): CoinDao

    abstract fun recentSearchDao(): RecentSearchDao

}