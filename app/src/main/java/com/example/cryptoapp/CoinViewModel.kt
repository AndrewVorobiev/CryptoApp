package com.example.cryptoapp

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.cryptoapp.api.ApiFactory
import com.example.cryptoapp.database.AppDatabase
import com.example.cryptoapp.pojo.CoinPriceInfo
import com.example.cryptoapp.pojo.CoinPriceInfoRawData
import com.google.gson.Gson
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class CoinViewModel(application: Application): AndroidViewModel(application) {

    private val compositeDisposable = CompositeDisposable()
    private val db = AppDatabase.getInstance(application)

    val priceList = db.coinPriceInfoDao().getPriceList()

    // В Котлин, можно сделать инициализацию метода внтруи ViewModel
    init {
        loadData()
    }

    fun getDetailInfo(fSym: String): LiveData<CoinPriceInfo> {
        return db.coinPriceInfoDao().getPriceInfoAboutCoin(fSym)
    }



    private fun loadData(){
        // сначала по Get запросу мы получаем Полный список
        val disposable = ApiFactory.apiService.getTopCoinsInfo()
            // Затем с помощью map преобразовываем этот список в строку
            .map { it.data?.map{ it.coinInfo?.name }?.joinToString(",").toString() }
                // С помощью этого метода мы получаем данные о монете
            .flatMap { ApiFactory.apiService.getFullPriceList(fSyms = it) }
            .map { getPriceListFromRawData(it) }
            // Выполняет работу метода через установленное время
            .delaySubscription(10,TimeUnit.SECONDS)
            // Повторяет загрузку метода, то уничтожения программы или сбоя интернета
            .repeat()
            // Перезагружает работу метода, если было получено исключение. Например отключили интернет
            .retry()
            .subscribeOn(Schedulers.io())
            .subscribe({
                // joinToString(",") - превращает коллекцию в строку
                db.coinPriceInfoDao().insertPriceList(it)
                Log.d("Main_Activity", "Success: $it")
            },{
                it.message?.let { it1 -> Log.d("Main_Activity", it1) }
            })
        compositeDisposable.add(disposable)
    }


    private fun getPriceListFromRawData(coinPriceInfoRawData: CoinPriceInfoRawData)
            : List<CoinPriceInfo> {
        val result = ArrayList<CoinPriceInfo>()
        val jsonObject = coinPriceInfoRawData.coinPriceInfoJsonObject ?: return result
        // Чтобы у Json-объекта получить набор ключей, то используется метод keySet()
        val coinKeySet = jsonObject.keySet()
        for (coinKey in coinKeySet) {
            val currencyJson = jsonObject.getAsJsonObject(coinKey)
            val currencyKeySet = currencyJson.keySet()
            for (currencyKey in currencyKeySet) {
                val priceInfo = Gson().fromJson(
                    currencyJson.getAsJsonObject(currencyKey),
                    CoinPriceInfo::class.java
                )
                result.add(priceInfo)
            }
        }
        return result
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}