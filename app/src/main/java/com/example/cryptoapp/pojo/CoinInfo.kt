package com.example.cryptoapp.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class CoinInfo(


    // https://min-api.cryptocompare.com/documentation - API - криптовалют
    // http://jsonviewer.stack.hu/ - для чтения json
    // Я использовал https://www.jsonschema2pojo.org/ для конвертарции POJO-объектов

    @SerializedName("Id")
    @Expose
    val id: String? = null,

    @SerializedName("Name")
    @Expose
    val name: String? = null,

    @SerializedName("FullName")
    @Expose
    val fullName: String? = null,

    @SerializedName("ImageUrl")
    @Expose
    val imageUrl: String? = null

)