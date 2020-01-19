package com.example.coinswatchermvvm.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.getActivity
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.util.Log
import com.example.coinswatchermvvm.CoinsWatcherApplication
import com.example.coinswatchermvvm.data.db.SignalEntry
import com.example.coinswatchermvvm.data.db.SignalsDao
import com.example.coinswatchermvvm.data.response.CoinResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import android.content.Context.NOTIFICATION_SERVICE
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.navigation.NavDeepLinkBuilder
import com.example.coinswatchermvvm.R
import com.example.coinswatchermvvm.data.repository.SignalsRepository
import com.example.coinswatchermvvm.ui.CoinsWatcherMVVMActivity
import com.example.coinswatchermvvm.ui.signal.SignalFragment

const val CURRENCY_SYMBOL = "symbol"

const val CURRENT_PRICE_BTC = "CURRENT_PRICE_BTC"

const val CURRENT_PRICE_USD = "CURRENT_PRICE_USD"

const val STOP_LOSS_BTC = "STOP_LOSS_BTC"

const val STOP_LOSS_USD = "STOP_LOSS_USD"

const val TAKE_PROFIT_BTC = "TAKE_PROFIT_BTC"

const val TAKE_PROFIT_USD = "TAKE_PROFIT_USD"

const val IS_STOP_LOSS_TYPE = "IS_STOP_LOSS_TYPE"

const val IS_USD_CURRENCY = "IS_USD_CURRENCY"

class SignalChecker(
//    private val signalsDao: SignalsDao,
    private val signalsRep: SignalsRepository,
    private val coinsWatcherApplication: CoinsWatcherApplication
) {

    private val LOG_TAG = "SignalChecker_debugLog_"

    private val context: Context = coinsWatcherApplication.baseContext

    lateinit var notificationManager: NotificationManager
    lateinit var notificationChannel: NotificationChannel
    lateinit var builder: Notification.Builder
    private val channelID = "com.example.coinswatchermvvm"
    private val description = "NotificationDescription"
    private var mNotificationId: Int = 1000


    fun checkSignals(list: List<CoinResponse>) {
        Log.d(LOG_TAG,"checkSignals +++++++++++++++++ LIST_SIZE: ${list.size}")


        GlobalScope.launch(Dispatchers.IO) {

            list.forEach{coin ->
//                val signalEntry: SignalEntry? = signalsDao.getSignalBySymbol(coin.symbol).value
                val signalEntry: SignalEntry? = signalsRep.getSignalBySymbol(coin.symbol).value
//                val signalEntry: SignalEntry? = signalsDao.getSignalBySymbol(coin.symbol)
                val signalEntry2: SignalEntry? = signalsRep.getSignalBySymbol2(coin.symbol)
                Log.d(LOG_TAG,"checkSignals REQUEST_SIGNAL_FOR_COIN: ${coin.symbol}  signalEntry: $signalEntry")
                Log.d(LOG_TAG,"checkSignals2 REQUEST_SIGNAL_FOR_COIN: ${coin.symbol}  signalEntry2: $signalEntry2")
                if(signalEntry2 != null) {
                    Log.d(LOG_TAG,"checkSignals FOUND_SIGNAL_FOR_COIN: ${coin.symbol} ")

                    if( (!(isZeroValue(signalEntry2.priceBtcStopLoss)))  && signalEntry2.priceBtcStopLoss > coin.priceBtc) {
                        addNotificationForCoin(coin, signalEntry2, true, false)
                    }

                    if( (!(isZeroValue(signalEntry2.priceBtcTakeProfit)))  && signalEntry2.priceBtcTakeProfit < coin.priceBtc) {
                        addNotificationForCoin(coin, signalEntry2, false, false)
                    }


                    if( (!(isZeroValue(signalEntry2.priceUsdStopLoss)))  && signalEntry2.priceUsdStopLoss > coin.priceUsd) {
                        addNotificationForCoin(coin, signalEntry2, true, true)
                    }

                    if( (!(isZeroValue(signalEntry2.priceUsdTakeProfit)))  && signalEntry2.priceUsdTakeProfit < coin.priceUsd) {
                        addNotificationForCoin(coin, signalEntry2, false, true)
                    }
//                    addNotificationForCoin(coin, signalEntry2, true, true)
                }

//                //
//                //TEST
//                if(coin.symbol.equals("BTC")) {
//                    addNotificationForCoinTesting()
//                }
//                //
            }

        }

    }


    private fun addNotificationForCoin(coin: CoinResponse, signalEntry: SignalEntry, isStopLoss: Boolean, isUsd: Boolean) {
        Log.d(LOG_TAG,"addNotificationForCoinTesting MAIN_ACTIVITY: ${CoinsWatcherApplication.getCoinsWatcherMVVMActivity()} ")
        notificationManager = coinsWatcherApplication.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


        var sign: String = if(isUsd) "\u0020" else "\u20BF"
        var typeSignal: String = if(isStopLoss) "Stop loss" else "Take profit"
        var msgtitle: String = "$sign $typeSignal $sign"
        var msgtext: String = "Current price of ${coin.symbol} is \$: ${coin.priceUsd} \u20BF: ${coin.priceBtc} "

//        val intent = Intent(this, SignalFragment::class.java)

//        val isStopLoss = false

        mNotificationId++


        val bundle = Bundle().apply {
            putDouble(CURRENT_PRICE_BTC, coin.priceBtc)
            putDouble(CURRENT_PRICE_USD, coin.priceUsd)
            putDouble(STOP_LOSS_BTC, signalEntry.priceBtcStopLoss)
            putDouble(STOP_LOSS_USD, signalEntry.priceUsdStopLoss)
            putDouble(TAKE_PROFIT_BTC, signalEntry.priceBtcTakeProfit)
            putDouble(TAKE_PROFIT_USD, signalEntry.priceUsdTakeProfit)
            putBoolean(IS_STOP_LOSS_TYPE, isStopLoss)
            putBoolean(IS_USD_CURRENCY, isUsd)
            putString(CURRENCY_SYMBOL, signalEntry.symbol)
        }

        val pendingIntent = NavDeepLinkBuilder(context)
            .setComponentName(CoinsWatcherMVVMActivity::class.java)
            .setGraph(R.navigation.mobile_navigation)
            .setDestination(R.id.signalFragment)
            .setArguments(bundle)
            .createPendingIntent()

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel =
                NotificationChannel(channelID, description, NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.GREEN
            notificationChannel.enableVibration(true)
            notificationManager.createNotificationChannel(notificationChannel)

            builder = Notification.Builder(coinsWatcherApplication.baseContext, channelID)
                .setContentTitle(msgtitle)
//                .setContentText("Current price of ${coin.symbol} is ${coin.priceUsd} USD")
                .setContentText(msgtext)

                .setSmallIcon(R.mipmap.ic_launcher_round)//.setSmallIcon(R.mipmap.ic_launcher_round)
//                .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.mipmap.ic_launcher))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setLights(Color.GREEN, 1000, 500)
                .setColor( if(isStopLoss) Color.RED else Color.GREEN)

        } else {

            builder = Notification.Builder(coinsWatcherApplication.baseContext)
                .setContentTitle(msgtitle)
//                .setContentText("Current price of BITCOIN \u20BF \u20BF \u20BF \u20BF \u20BF")
                .setContentText(msgtext)
                .setSmallIcon(R.mipmap.ic_launcher_round)
//                .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.mipmap.ic_launcher))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
        }

        notificationManager.notify(mNotificationId, builder.build())

    }


    private fun isZeroValue(dVal: Double) : Boolean {
        val minimal: Double = 0.0000000000000001
        if(dVal < minimal) {
            return true
        }
        return false
    }

}