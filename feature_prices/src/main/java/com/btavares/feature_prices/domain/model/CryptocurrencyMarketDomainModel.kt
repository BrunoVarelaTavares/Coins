package com.btavares.feature_prices.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
internal data class CryptocurrencyMarketDomainModel(
    val id : String?,
    val symbol : String?,
    val name : String?,
    val image : String?,
    val currentPrice : Double?,
    val marketCap : Long?,
    val marketCapRank : Long?,
    val totalVolume : Double?,
    val highTwentyFourHours : Double?,
    val lowTwentyFourHours : Double?,
    val priceChangeTwentyFourHours : Double?,
    val priceChangePercentageTwentyFourHours : Double?,
    val marketCapChangeTwentyFourHours : Double?,
    val marketCapChangePercentageTwentyFourHours : Double?,
    val circulatingSupply : Double?,
    val totalSupply : Double?,
    val ath : Double?,
    val athChangePercentage : Double?,
    val athDate : String?,
    val atl : Double?,
    val atlChangePercentage : Double?,
    val atlDate : String?,
    val lastUpdated : String?,
    var nativeCurrencySymbol : String

) : Parcelable