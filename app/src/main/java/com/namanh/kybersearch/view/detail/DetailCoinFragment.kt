package com.namanh.kybersearch.view.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.gson.Gson
import com.namanh.kybersearch.data.model.Coin
import com.namanh.kybersearch.databinding.FragmentDetailCoinBinding
import com.namanh.kybersearch.helper.GlideHelper
import com.namanh.kybersearch.utils.AppUtils
import com.namanh.kybersearch.utils.LogUtil
import com.namanh.kybersearch.view.main.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailCoinFragment : BaseFragment<FragmentDetailCoinBinding>() {

    companion object {
        fun newInstance() = DetailCoinFragment()
    }

    private val viewModel: DetailCoinViewModel by viewModels()

    override fun viewBindingInflate(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentDetailCoinBinding.inflate(inflater, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val result = arguments?.get(AppUtils.COIN_KEY) as String
        try {
            val coin = Gson().fromJson(result, Coin::class.java)
            if (coin != null) {
                binding.txtSymbol.text = coin.symbol
                binding.txtName.text = coin.name
                GlideHelper.loadImage(context, binding.imgLogo, coin.logo)

                viewModel.addCoinToRecent(coin)
            }
        } catch (exception: Exception) {
            LogUtil.e("Exception: ${exception.message}")
        }
    }

}