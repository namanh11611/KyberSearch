package com.namanh.kybersearch.data.model

import androidx.room.*

@Entity(tableName = "coin")
data class Coin(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "rowid") val rowid: Int,
    @ColumnInfo(name = "address") val address: String,
    @ColumnInfo(name = "symbol") val symbol: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "decimals") val decimals: Int,
    @ColumnInfo(name = "logo") val logo: String
)
