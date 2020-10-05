package com.btavares.feature_coin_detail.data.model

import android.annotation.SuppressLint
import com.btavares.feature_coin_detail.domain.model.CryptocurrencyMarketChartDomainModel
import java.text.SimpleDateFormat
import java.util.*

const val indexZero =  0
const val indexOne =  1


internal data class CryptocurrencyMarketChartDataModel(
    val prices : List<List<Double>>,
    val market_caps : List<List<Double>>,
    val total_volumes : List<List<Double>>
)


internal fun CryptocurrencyMarketChartDataModel.toDomainModel() : CryptocurrencyMarketChartDomainModel {
    val pricesList = convertListToPriceChartDataModel().map { it.toDomainModel() }
    val marketCapsList = convertListToCoinMarketChartDataModel().map { it.toDomainModel() }
    val totalValuesList = convertListToTotalVolumeChartDataModel().map { it.toDomainModel() }

    return CryptocurrencyMarketChartDomainModel(
        prices = pricesList,
        marketCaps = marketCapsList,
        totalVolumes = totalValuesList

    )
}

internal fun CryptocurrencyMarketChartDataModel.convertListToPriceChartDataModel() : List<PriceChartDataModel>{
    val list = mutableListOf<PriceChartDataModel>()
    for (item in prices){
        if (item.isNotEmpty() && item.size > indexOne){
                list.add(PriceChartDataModel(getDateTimeFromTimestamp(item[indexZero].toLong()), item[indexOne]))
        }
    }
    return list
}


internal fun CryptocurrencyMarketChartDataModel.convertListToCoinMarketChartDataModel() : List<MarketCapChartDataModel>{
    val list = mutableListOf<MarketCapChartDataModel>()
    for (item in market_caps){
        if (item.isNotEmpty() && item.size > indexOne){
                list.add(MarketCapChartDataModel(getDateTimeFromTimestamp(item[indexZero].toLong()), item[indexOne]))
        }
    }

    return list
}


internal fun CryptocurrencyMarketChartDataModel.convertListToTotalVolumeChartDataModel() : List<TotalVolumeMarketChartDataModel>{
    val list = mutableListOf<TotalVolumeMarketChartDataModel>()
    for (item in total_volumes){
        if (item.isNotEmpty() && item.size > indexOne){
                list.add(TotalVolumeMarketChartDataModel(getDateTimeFromTimestamp(item[indexZero].toLong()), item[indexOne]))
        }
    }
    return list
}

@SuppressLint("SimpleDateFormat")
private fun getDateTimeFromTimestamp(s: Long): String? {
    return try {
        val sdf = SimpleDateFormat("MM/dd/yyyy HH:mm")
        val netDate = Date(s)
        sdf.format(netDate)
    } catch (e: Exception) {
        ""
    }
}