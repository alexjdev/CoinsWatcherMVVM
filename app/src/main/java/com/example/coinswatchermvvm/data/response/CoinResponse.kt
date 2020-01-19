package com.example.coinswatchermvvm.data.response


import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "coins")
//@Entity(tableName = "coins", indices = [Index(value = ["rank"], unique = true)])
data class CoinResponse(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("symbol")
    val symbol: String,
    @SerializedName("rank")
    @PrimaryKey(autoGenerate = false)
    val rank: Int,
    @SerializedName("price_usd")
    val priceUsd: Double,
    @SerializedName("price_btc")
    val priceBtc: Double,
    @SerializedName("24h_volume_usd")
    val volumeUsd_24h: Double,
    @SerializedName("market_cap_usd")
    val marketCapUsd: Double,
    @SerializedName("available_supply")
    val availableSupply: Double,
    @SerializedName("total_supply")
    val totalSupply: Double,
    @SerializedName("max_supply")
    val maxSupply: Double,
    @SerializedName("percent_change_1h")
    val percentChange1h: Double,
    @SerializedName("percent_change_24h")
    val percentChange24h: Double,
    @SerializedName("percent_change_7d")
    val percentChange7d: Double,
    @SerializedName("last_updated")
    val lastUpdated: Long
) {
}