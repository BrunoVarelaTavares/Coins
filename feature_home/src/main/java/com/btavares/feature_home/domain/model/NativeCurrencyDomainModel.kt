package com.btavares.feature_home.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
internal data class NativeCurrencyDomainModel(
    val currencyCode : String,
    val currencySymbol: String) : Parcelable