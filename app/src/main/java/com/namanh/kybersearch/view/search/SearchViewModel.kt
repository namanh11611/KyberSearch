package com.namanh.kybersearch.view.search

import androidx.lifecycle.*
import com.namanh.kybersearch.data.model.Coin
import com.namanh.kybersearch.data.model.RecentSearch
import com.namanh.kybersearch.data.repository.CoinRepository
import com.namanh.kybersearch.data.repository.ResourceState
import com.namanh.kybersearch.utils.LogUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val coinRepo : CoinRepository
): ViewModel() {

    private val _forceUpdate = MutableLiveData<Boolean>(true)
    val items: LiveData<List<Coin>> = _forceUpdate.switchMap { forceUpdate ->
        if (forceUpdate) {
            viewModelScope.launch {
                coinRepo.getCoins()
            }
        }
        coinRepo.observeCoins(viewModelScope.coroutineContext).distinctUntilChanged().switchMap { handleCoins(it) }
    }

    val itemsRecentSearch: LiveData<List<Coin>> = _forceUpdate.switchMap {
        coinRepo.observeCoinsRecent(viewModelScope.coroutineContext).distinctUntilChanged().switchMap { handleCoins(it) }
    }

    fun forceUpdate() {
        _forceUpdate.value = true
    }

    fun searchCoin(text: String) {
        viewModelScope.launch {
            coinRepo.searchCoins("$text%")
        }
    }

    private fun handleCoins(coinResult: ResourceState<List<Coin>>): LiveData<List<Coin>> {
        LogUtil.d("handleCoins")
        val result = MutableLiveData<List<Coin>>()
        when (coinResult) {
            is ResourceState.Success -> result.value = coinResult.getCurrentData()
        }
        return result
    }

}