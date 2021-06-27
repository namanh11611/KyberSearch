package com.namanh.kybersearch.view.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.namanh.kybersearch.R
import com.namanh.kybersearch.data.model.Coin
import com.namanh.kybersearch.databinding.ItemCoinBinding
import com.namanh.kybersearch.helper.GlideHelper
import com.namanh.kybersearch.utils.AppUtils
import com.namanh.kybersearch.utils.LogUtil

class CoinAdapter(var dataSet: List<Coin>) :
    RecyclerView.Adapter<CoinAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ItemCoinBinding) : RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {

        private lateinit var mCoin: Coin

        init {
            binding.root.setOnClickListener(this)
        }

        fun bind(coin: Coin) {
            mCoin = coin
            binding.txtSymbol.text = coin.symbol
            binding.txtName.text = coin.name
//            LogUtil.d("Symbol ${coin.symbol} - logo ${coin.logo}")
            GlideHelper.loadImage(binding.root.context, binding.imgLogo, coin.logo)
        }

        override fun onClick(v: View?) {
            val bundle = bundleOf(AppUtils.COIN_KEY to Gson().toJson(mCoin))
            binding.root.findNavController()
                .navigate(R.id.action_searchFragment_to_detailCoinFragment, bundle)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCoinBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataSet[position])
    }

    override fun getItemCount() = dataSet.size
}