package com.btavares.feature_settings.data.model

import com.squareup.moshi.Json

data class Rates (
    @field:Json(name = "HKD") val hKD : Double,
    @field:Json(name = "ISK") val iSK : Double,
    @field:Json(name = "PHP") val pHP : Double,
    @field:Json(name = "DKK") val dKK : Double,
    @field:Json(name = "HUF") val hUF : Double,
    @field:Json(name = "CZK") val cZK : Double,
    @field:Json(name = "AUD") val aUD : Double,
    @field:Json(name = "RON") val rON : Double,
    @field:Json(name = "SEK") val sEK : Double,
    @field:Json(name = "IDR") val iDR : Double,
    @field:Json(name = "INR") val iNR : Double,
    @field:Json(name = "BRL") val bRL : Double,
    @field:Json(name = "RUB") val rUB : Double,
    @field:Json(name = "HRK") val hRK : Double,
    @field:Json(name = "JPY") val jPY : Double,
    @field:Json(name = "THB") val tHB : Double,
    @field:Json(name = "CHF") val cHF : Double,
    @field:Json(name = "SGD") val sGD : Double,
    @field:Json(name = "PLN") val pLN : Double,
    @field:Json(name = "BGN") val bGN : Double,
    @field:Json(name = "TRY") val tRY : Double,
    @field:Json(name = "CNY") val cNY : Double,
    @field:Json(name = "NOK") val nOK : Double,
    @field:Json(name = "NZD") val nZD : Double,
    @field:Json(name = "ZAR") val zAR : Double,
    @field:Json(name = "USD") val uSD : Double,
    @field:Json(name = "MXN") val mXN : Double,
    @field:Json(name = "ILS") val iLS : Double,
    @field:Json(name = "GBP") val gBP : Double,
    @field:Json(name = "KRW") val kRW : Double,
    @field:Json(name = "MYR") val mYR : Double
    //https://api.exchangeratesapi.io/latest?&base=USD
)