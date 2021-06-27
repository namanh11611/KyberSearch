package com.namanh.kybersearch.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.namanh.kybersearch.data.database.CoinDao
import com.namanh.kybersearch.data.database.RecentSearchDao
import com.namanh.kybersearch.data.model.Coin
import com.namanh.kybersearch.data.model.RecentSearch
import com.namanh.kybersearch.data.service.ApiService
import com.namanh.kybersearch.di.DispatcherIO
import com.namanh.kybersearch.utils.LogUtil
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class CoinRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val coinDao: CoinDao,
    private val recentSearchDao: RecentSearchDao,
    @DispatcherIO private val dispatcherIO: CoroutineDispatcher
) : CoinRepository {

    private val observableCoin = MutableLiveData<ResourceState<List<Coin>>>()
    private val observableCoinRecent = MutableLiveData<ResourceState<List<Coin>>>()

    override suspend fun getCoins() {
        val remoteData = getCoinsFromRemote()
        if (remoteData is ResourceState.Success) {
            observableCoin.value = remoteData
            remoteData.getCurrentData()?.let { saveDataToDatabase(it) }
        } else {
            observableCoin.value = getCoinsFromDatabase()
        }
    }

    override suspend fun searchCoins(text: String) {
        try {
            val result = coinDao.searchBySymbolOrName(text)
            LogUtil.d("searchCoins $result")
            observableCoin.value = ResourceState.Success(result)
        } catch (exception: Exception) {
            LogUtil.e("Search Exception: ${exception.message}")
        }
    }

    override fun observeCoins(coroutineContext: CoroutineContext): LiveData<ResourceState<List<Coin>>> {
        return observableCoin
    }

    override fun observeCoinsRecent(coroutineContext: CoroutineContext): LiveData<ResourceState<List<Coin>>> {
        return observableCoinRecent
    }

    override suspend fun addCoinToRecentSearch(coin: Coin) = withContext(dispatcherIO) {
        recentSearchDao.insert(RecentSearch(coin.symbol))
        try {
            val result = recentSearchDao.getAll()
            val coinList: MutableList<Coin> = mutableListOf()
            for (coin in result) {
                coinList.add(coinDao.searchBySymbol(coin.symbol))
            }
            observableCoinRecent.postValue(ResourceState.Success(coinList.reversed()))
        } catch (exception: Exception) {
            LogUtil.e("Recent Exception: ${exception.message}")
        }
    }

    private suspend fun getCoinsFromRemote(): ResourceState<List<Coin>> =
        withContext(dispatcherIO) {
            try {
                val response = apiService.getCoins()
                LogUtil.d("getCoinsFromRemote Success")
                ResourceState.Success(response.tokens)
            } catch (exception: Exception) {
                LogUtil.e("getCoinsFromRemote Exception: ${exception.message}")
                ResourceState.Error("Remote Exception: ${exception.message}")
            }
        }

    private suspend fun getCoinsFromDatabase(): ResourceState<List<Coin>> =
        withContext(dispatcherIO) {
            try {
                val result = coinDao.getAll()
                LogUtil.d("getCoinsFromDatabase Success")
                ResourceState.Success(result)
            } catch (exception: Exception) {
                LogUtil.e("getCoinsFromDatabase Exception: ${exception.message}")
                ResourceState.Error("Database Exception: ${exception.message}")
            }
        }

    private suspend fun saveDataToDatabase(coins: List<Coin>) = withContext(dispatcherIO) {
        try {
            coinDao.deleteAll()
            coinDao.insertAll(coins)
        } catch (exception: Exception) {
            LogUtil.e("Save database Exception: ${exception.message}")
        }
    }
}