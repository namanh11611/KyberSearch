package com.namanh.kybersearch.view.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.namanh.kybersearch.data.model.Coin
import com.namanh.kybersearch.data.model.RecentSearch
import com.namanh.kybersearch.databinding.FragmentSearchBinding
import com.namanh.kybersearch.utils.LogUtil
import com.namanh.kybersearch.utils.NetworkUtils
import com.namanh.kybersearch.view.main.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import java.util.regex.Pattern

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(), View.OnClickListener, SearchView.OnQueryTextListener {

    companion object {
        val sPattern: Pattern = Pattern.compile("^([A-Za-z0-9 ])+")

        fun newInstance() = SearchFragment()
    }

    enum class State {
        NO_INTERNET, LOADING, LOADED
    }

    private val viewModel: SearchViewModel by viewModels()
    private val mCoinAdapter = CoinAdapter(emptyList())
    private val mCoinRecentAdapter = CoinSimpleAdapter(emptyList())
    private val mCoinSuggestionAdapter = CoinSimpleAdapter(emptyList())

    override fun viewBindingInflate(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentSearchBinding.inflate(inflater, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.btReconnect.setOnClickListener(this)
        binding.searchView.setOnQueryTextListener(this)
        binding.listCoin.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.listCoin.adapter = mCoinAdapter
        binding.listCoinRecent.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.listCoinRecent.adapter = mCoinRecentAdapter
        binding.listCoinSuggestion.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.listCoinSuggestion.adapter = mCoinSuggestionAdapter

        observeCoin()

        if (context?.let { NetworkUtils.isNetworkConnected(it) } == false) {
//            setState(State.NO_INTERNET)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
//            R.id.bt_reconnect -> viewModel.fetchCoin(viewLifecycleOwner)
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        LogUtil.d("onQueryTextSubmit $query")
//        if (query != null) {
//            viewModel.searchCoin(query)
//        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        LogUtil.d("onQueryTextChange $newText")
        if (newText != null && newText.isNotEmpty()) {
            if (sPattern.matcher(newText).matches()) {
                binding.txtInvalid.visibility = View.INVISIBLE
                viewModel.searchCoin(newText)
            } else {
                binding.txtInvalid.visibility = View.VISIBLE
            }
        } else {
            binding.txtInvalid.visibility = View.INVISIBLE
        }
        return true
    }

    private fun observeCoin() {
//        viewModel.forceUpdate()
        viewModel.items.observe(viewLifecycleOwner, { result ->
            LogUtil.d("observeCoin ${result.size}")
            if (result == null) return@observe
            setState(State.LOADED)
            mCoinAdapter.dataSet = result
            mCoinAdapter.notifyDataSetChanged()

            mCoinSuggestionAdapter.dataSet = result
            mCoinSuggestionAdapter.notifyDataSetChanged()
        })

        viewModel.itemsRecentSearch.observe(viewLifecycleOwner, { result ->
            LogUtil.d("observe coin recent ${result.size}")
            if (result == null) return@observe
            setState(State.LOADED)
            mCoinRecentAdapter.dataSet = result
            mCoinRecentAdapter.notifyDataSetChanged()
        })
    }

    private fun setState(state: State) {
        binding.listCoin.visibility = if (state == State.LOADED) View.VISIBLE else View.GONE
        binding.pbLoading.visibility = if (state == State.LOADING) View.VISIBLE else View.GONE
        binding.txtNoConnect.visibility = if (state == State.NO_INTERNET) View.VISIBLE else View.GONE
        binding.btReconnect.visibility = if (state == State.NO_INTERNET) View.VISIBLE else View.GONE
    }

}