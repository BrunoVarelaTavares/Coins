package com.btavares.feature_portfolio.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
internal data class NativeCurrencyDomainModel(
    val currencyCode : String = "EUR",
    val currencySymbol: String = "€") : Parcelable