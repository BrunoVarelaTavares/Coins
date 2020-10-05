package com.btavares.feature_database.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "native_currency")
data class NativeCurrency(
/*    @PrimaryKey(autoGenerate = false)
    val nativeCurrencyId : Long?,*/
    @PrimaryKey(autoGenerate = false)
    val currencyCode: String,

    @ColumnInfo(name = "currency_symbol")
    val currencySymbol: String
)