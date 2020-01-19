package com.example.coinswatchermvvm.ui.signalslist

import com.example.coinswatchermvvm.R
import com.example.coinswatchermvvm.data.db.SignalEntry
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.signals_row.*

class SignalItem(
    val signalEntry: SignalEntry
) : Item() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.apply {
            updateState()
        }
    }

    override fun getLayout() = R.layout.signals_row

    private fun ViewHolder.updateState() {
        textView_signal_symbol.text = signalEntry.symbol
        textView_signal_usd_take_profit.text = "\$: ${signalEntry.priceUsdTakeProfit}"
        textView_signal_usd_stop_loss.text = "\$: ${signalEntry.priceUsdStopLoss}"
        textView_signal_btc_take_profit.text = "\u20BF: ${signalEntry.priceBtcTakeProfit}"
        textView_signal_btc_stop_loss.text = "\u20BF: ${signalEntry.priceBtcStopLoss}"

//        textView_signal_usd_take_profit.text = "\$: " + signalEntry.priceUsdTakeProfit.toString()
//        textView_signal_usd_stop_loss.text = "\$: " + signalEntry.priceUsdStopLoss.toString()
//        textView_signal_btc_take_profit.text = "\u20BF: " + signalEntry.priceBtcTakeProfit.toString()
//        textView_signal_btc_stop_loss.text = "\u20BF: " + signalEntry.priceBtcStopLoss.toString()

//        textView_signal_usd_take_profit.text = signalEntry.priceUsdTakeProfit.toString()
//        textView_signal_usd_stop_loss.text = signalEntry.priceUsdStopLoss.toString()
//        textView_signal_btc_take_profit.text = signalEntry.priceBtcTakeProfit.toString()
//        textView_signal_btc_stop_loss.text = signalEntry.priceBtcStopLoss.toString()
    }

}
