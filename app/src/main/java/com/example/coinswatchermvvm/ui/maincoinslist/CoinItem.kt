package com.example.coinswatchermvvm.ui.maincoinslist

import android.graphics.Color
import com.example.coinswatchermvvm.R
import com.example.coinswatchermvvm.data.response.CoinResponse
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.coin_row.*


class CoinItem(
    val coinResponse: CoinResponse
) : Item() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.apply {
            updateState()
        }
    }

    override fun getLayout() = R.layout.coin_row

    private fun ViewHolder.updateState() {

        textView_coin_rank.text = coinResponse.rank.toString()
        textView_coin_name.text = coinResponse.name
        textView_coin_symbol.text = coinResponse.symbol
        textView_coin_percentage.text = coinResponse.percentChange24h.toString()
        textView_coin_percentage.setTextColor( if(coinResponse.percentChange24h < 0) Color.RED else Color.GREEN)
        textView_coin_usd_price.text = "$ ${coinResponse.priceUsd.toString()}"
        textView_coin_cap.text = "Market Cap. $ ${coinResponse.marketCapUsd.toString()} Bn"
    }

//    private fun ViewHolder.updateImage() {
//        GlideApp.with(this.containerView)
//            .load("http:" + iconUrl)
//            .into(imageView_coin_icon)
//    }
}


