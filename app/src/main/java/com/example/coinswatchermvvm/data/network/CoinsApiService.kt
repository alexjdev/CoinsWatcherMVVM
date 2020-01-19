package com.example.coinswatchermvvm.data.network

import android.util.Log
import com.example.coinswatchermvvm.data.response.CoinResponse
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

private val LOG_TAG = "CoinsApiService_debugLog_"

interface CoinsApiService {


    @GET("15tntl")  //FOR TESTING ONLY
//    @GET("ticker/?limit=100")
    fun getCoinsList() : Deferred<List<CoinResponse>>

    companion object {
        operator fun invoke(
            connectivityInterceptor: ConnectivityInterceptor
        ): CoinsApiService {
            val requestInterceptor = Interceptor { chain ->

                val url = chain.request()
                    .url()
                    .newBuilder()
                    //.addQueryParameter("key", API_KEY)
                    .build()
                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()

                Log.d(LOG_TAG,"Interceptor_invoke")

                return@Interceptor chain.proceed(request)
            }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .addInterceptor(connectivityInterceptor)
                .build()


            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://api.myjson.com/bins/")  //FOR TESTING ONLY
//                .baseUrl("https://api.coinmarketcap.com/v1/")
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(CoinsApiService::class.java)
        }
    }

}