package com.namanh.kybersearch.data.model.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.namanh.kybersearch.data.model.Coin
import com.namanh.kybersearch.data.model.RecentSearch

data class CoinAndRecentSearch(
    @Embedded val coin: Coin,
    @Relation(
        parentColumn = "symbol",
        entityColumn = "symbol"
    )
    val recentSearch: RecentSearch
)
