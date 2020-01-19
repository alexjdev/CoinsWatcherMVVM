package com.example.coinswatchermvvm.ui.detail

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.Navigation

import com.example.coinswatchermvvm.R
import com.example.coinswatchermvvm.data.response.CoinResponse
import com.example.coinswatchermvvm.ui.ScopedFragment
import kotlinx.android.synthetic.main.coin_detail_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.factory
import org.kodein.di.generic.instance

class CoinDetailFragment : ScopedFragment(), KodeinAware {

    override val kodein by closestKodein()

    private val viewModelFactoryInstanceFactory
            : ((Int) -> CoinDetailViewModelFactory) by factory()

    private lateinit var viewModel: CoinDetailViewModel

    private val LOG_TAG = "CoinDetailFragment_debugLog_"

    private lateinit var symbolDetailedCoin: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.coin_detail_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val safeArgs = arguments?.let { CoinDetailFragmentArgs.fromBundle(it) }
        val rank = safeArgs?.rank

        viewModel = ViewModelProviders.of(this, viewModelFactoryInstanceFactory(rank!!))
            .get(CoinDetailViewModel::class.java)

        bindUI()

        buttonAddSignal.setOnClickListener {


            val actionAddSignal = CoinDetailFragmentDirections.actionAddSignal(symbolDetailedCoin)
            actionAddSignal.symbol = symbolDetailedCoin

            Navigation.findNavController(it).navigate(actionAddSignal)
        }
    }


    private fun bindUI() = launch(Dispatchers.Main) {

        val coinResponse = viewModel.coinResponse.await()

        coinResponse.observe(this@CoinDetailFragment, Observer {
            if (it == null) return@Observer

            textView_rank.text = "Rank: ${coinResponse.value!!.rank}"
            textView_symbol.text = "Symbol: ${coinResponse.value!!.symbol}"
            textView_name.text = "Name: ${coinResponse.value!!.name}"
            textView_id.text = "Identifier: ${coinResponse.value!!.id}"
            textView_price_usd.text = "Price USD: ${coinResponse.value!!.priceUsd}"
            textView_price_btc.text = "Price BTC: ${coinResponse.value!!.priceBtc}"
            textView_volume24h_usd.text = "Volume last 24h(in USD): ${coinResponse.value!!.volumeUsd_24h}"
            textView_market_cap.text = "Current market capitalization(Bn): ${coinResponse.value!!.marketCapUsd}"
            textView_available_supply.text = "Available supply: ${coinResponse.value!!.availableSupply}"
            textView_total_supply.text = "Total supply: ${coinResponse.value!!.totalSupply}"

            symbolDetailedCoin = coinResponse.value!!.symbol
        })

    }

}
