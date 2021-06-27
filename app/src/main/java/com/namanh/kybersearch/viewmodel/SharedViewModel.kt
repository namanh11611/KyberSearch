package com.namanh.kybersearch.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.namanh.kybersearch.data.model.Coin
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedViewModel @Inject constructor() : ViewModel() {
    private val mCoin = MutableLiveData<Coin>()

    fun selectCoin(coin: Coin) {
        mCoin.value = coin
    }

    fun getCoin(): Coin? {
        return mCoin.value
    }
}