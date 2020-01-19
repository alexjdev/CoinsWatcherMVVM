package com.example.coinswatchermvvm

import android.app.Application
import android.content.Context
import androidx.preference.PreferenceManager
import com.example.coinswatchermvvm.data.db.CoinsWatcherDatabase
import com.example.coinswatchermvvm.data.network.*
import com.example.coinswatchermvvm.data.repository.CoinsRepository
import com.example.coinswatchermvvm.data.repository.CoinsRepositoryImpl
import com.example.coinswatchermvvm.data.repository.SignalsRepository
import com.example.coinswatchermvvm.data.repository.SignalsRepositoryImpl
import com.example.coinswatchermvvm.ui.CoinsWatcherMVVMActivity
import com.example.coinswatchermvvm.ui.addsignal.AddEditSignalViewModelFactory
import com.example.coinswatchermvvm.ui.detail.CoinDetailViewModelFactory
import com.example.coinswatchermvvm.ui.maincoinslist.MainCoinsListViewModelFactory
import com.example.coinswatchermvvm.ui.settings.SettingsViewModelFactory
import com.example.coinswatchermvvm.ui.signal.SignalViewModelFactory
import com.example.coinswatchermvvm.ui.signalslist.SignalsListViewModelFactory
import com.example.coinswatchermvvm.utils.CoinsPreferences
import com.example.coinswatchermvvm.utils.SignalChecker
//import android.preference.PreferenceManager
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.*

class CoinsWatcherApplication : Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@CoinsWatcherApplication))

        bind() from singleton { CoinsPreferences(instance()) }//TODO: check it

        bind() from singleton { CoinsWatcherDatabase(instance()) }
        bind() from singleton { instance<CoinsWatcherDatabase>().coinsDao() }
        bind() from singleton { instance<CoinsWatcherDatabase>().signalsDao() } //
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton { CoinsApiService(instance()) }
        bind<CoinsNetworkDataSource>() with singleton { CoinsNetworkDataSourceImpl(instance()) }
//        bind<CoinsRepository>() with singleton { CoinsRepositoryImpl(instance(), instance()) }

        bind<SignalsRepository>() with singleton { SignalsRepositoryImpl(instance()) }//

        bind() from singleton { SignalChecker(instance(), /*instance()*/ this@CoinsWatcherApplication) }//TODO: NOT USED YET

        bind<CoinsRepository>() with singleton { CoinsRepositoryImpl(instance(), instance(), instance(), instance()) }

//        bind<SignalsRepository>() with singleton { SignalsRepositoryImpl(instance()) }//

        bind() from provider { MainCoinsListViewModelFactory(instance()) }

        bind() from factory { rank: Int -> CoinDetailViewModelFactory(rank, instance()) }

        bind() from singleton /*provider*/ { SettingsViewModelFactory(instance()) }//TODO: check it, try make singleton?

        bind() from factory { symbol: String -> AddEditSignalViewModelFactory(symbol, instance()) } //TODO: check it

        bind() from provider { SignalsListViewModelFactory(instance()) }//

//        bind() from factory { SignalViewModelFactory( instance()) } //TODO: check it
    }


    init {
        instance = this
        coinsWatcherMVVMActivity = CoinsWatcherMVVMActivity()
    }

    companion object {
        private var instance: CoinsWatcherApplication? = null

        private var coinsWatcherMVVMActivity: CoinsWatcherMVVMActivity? = null

        fun applicationContext() : Context {
            return instance!!.applicationContext
        }

        public fun getCoinsWatcherMVVMActivity() : CoinsWatcherMVVMActivity {
            return coinsWatcherMVVMActivity!!
        }
    }

    ///



    override fun onCreate() {
        super.onCreate()

//        this.baseContext//?????

        //PreferenceManager.setDefaultValues(this, R.xml.preferences, false) //TODO: change file with pref.
//        AndroidThreeTen.init(this)
//        PreferenceManager.setDefaultValues(this, R.xml.preferences, false)
    }
}