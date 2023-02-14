package com.example.cryptoapp.api

import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

// В Kotlin нет необходимости реализовывать Singlton,
// то есть чтобы Retrofit был один на весь проект.
// Так как класс Object его реализует

object ApiFactory {

    private const val BASE_URL = "https://min-api.cryptocompare.com/data/"

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .baseUrl(BASE_URL)
        .build()

    val apiService = retrofit.create(ApiService::class.java)
}
