package com.example.cryptoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

class CoinPriceActivity : AppCompatActivity() {

    private lateinit var viewModel: CoinViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coin_price_list)
        viewModel = ViewModelProvider(this)[CoinViewModel::class.java]

//        viewModel.priceList.observe(this, Observer {
//            Log.d("TEST_LOADING_DATA", "Success in Activity: + $it")
//        })

        viewModel.getDetailInfo("BTC").observe(this, Observer{
            Log.d("TEST_LOADING_DATA", "Success in getDetailInfo: + $it")
        })
    }
}