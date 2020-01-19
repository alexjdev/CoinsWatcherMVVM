package com.example.coinswatchermvvm.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "signals")
data class SignalEntry(

    @SerializedName("symbol")
    @PrimaryKey(autoGenerate = false)
    val symbol: String,

    @SerializedName("price_usd_take")
    val priceUsdTakeProfit: Double,

    @SerializedName("price_usd_stop")
    val priceUsdStopLoss: Double,

    @SerializedName("price_btc_take")
    val priceBtcTakeProfit: Double,

    @SerializedName("price_btc_stop")
    val priceBtcStopLoss: Double
) {
    override fun toString(): String {
        return "symbol: $symbol priceUsdTakeProfit: $priceUsdTakeProfit priceUsdStopLoss: $priceUsdStopLoss " +
                "priceBtcTakeProfit: $priceBtcTakeProfit priceBtcStopLoss: $priceBtcStopLoss"
    }
}