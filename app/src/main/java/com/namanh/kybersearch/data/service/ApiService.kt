package com.namanh.kybersearch.data.service

import com.namanh.kybersearch.data.model.CoinResponse
import retrofit2.http.GET

interface ApiService {

    @GET("tokenList")
    suspend fun getCoins(): CoinResponse

}