package com.namanh.kybersearch.data.repository

import androidx.lifecycle.LiveData
import com.namanh.kybersearch.data.model.Coin
import com.namanh.kybersearch.data.model.RecentSearch
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Singleton
interface CoinRepository {

    suspend fun getCoins()

    suspend fun searchCoins(text: String)

    suspend fun addCoinToRecentSearch(coin: Coin)

    fun observeCoins(coroutineContext: CoroutineContext): LiveData<ResourceState<List<Coin>>>

    fun observeCoinsRecent(coroutineContext: CoroutineContext): LiveData<ResourceState<List<Coin>>>

}