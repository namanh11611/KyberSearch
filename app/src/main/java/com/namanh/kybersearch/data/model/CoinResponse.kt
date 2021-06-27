package com.namanh.kybersearch.data.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class CoinResponse(
    @SerializedName("timestamp") val timestamp: Long,
    @SerializedName("tokens") val tokens: List<Coin>
)
