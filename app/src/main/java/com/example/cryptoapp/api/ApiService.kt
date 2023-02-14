package com.example.cryptoapp.api

import com.example.cryptoapp.pojo.CoinInfoListOfData
import com.example.cryptoapp.pojo.CoinPriceInfoRawData
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query


// В данном классе хрянатся все методы по работе с сетью

interface ApiService {

    @GET("top/totalvolfull")
    fun getTopCoinsInfo(
        @Query(QUERY_PARAM_API_KEY) apiKey: String = "5b451a010f1b8acaa7ce66646bb3e0aa409b7da03bc5c9dd23ed499d53cdf2bb",
        @Query(QUERY_PARAM_LIMIT) limit: Int = 10,
        @Query(QUERY_PARAM_TO_SYMBOL) tSym: String = CURRENCY
    ): Single<CoinInfoListOfData>


    @GET("pricemultifull")
    fun getFullPriceList(
        @Query(QUERY_PARAM_API_KEY) apiKey: String = "5b451a010f1b8acaa7ce66646bb3e0aa409b7da03bc5c9dd23ed499d53cdf2bb",
        @Query(QUERY_PARAM_FROM_SYMBOLS) fSyms: String,
        @Query(QUERY_PARAM_TO_SYMBOLS) tSyms: String = CURRENCY
    ): Single<CoinPriceInfoRawData>




    companion object{
        private const val QUERY_PARAM_LIMIT = "limit"
        private const val QUERY_PARAM_TO_SYMBOL = "tsym"
        private const val QUERY_PARAM_API_KEY = "api_key"
        private const val CURRENCY = "USD"


        private const val QUERY_PARAM_FROM_SYMBOLS = "fsyms"
        private const val QUERY_PARAM_TO_SYMBOLS = "tsyms"

    }
}