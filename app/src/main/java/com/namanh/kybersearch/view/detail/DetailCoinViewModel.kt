package com.namanh.kybersearch.view.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.namanh.kybersearch.data.model.Coin
import com.namanh.kybersearch.data.repository.CoinRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailCoinViewModel @Inject constructor(
    private val coinRepo : CoinRepository
): ViewModel() {

    fun addCoinToRecent(coin: Coin) {
        viewModelScope.launch {
            coinRepo.addCoinToRecentSearch(coin)
        }
    }

}