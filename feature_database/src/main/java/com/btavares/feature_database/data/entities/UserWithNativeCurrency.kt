package com.btavares.feature_database.data.entities

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class UserWithNativeCurrency(
    @Embedded val user: User,
    @Relation(
        parentColumn = "id",
        entityColumn = "currencyCode",
        associateBy = Junction(UserNativeCurrency::class)
    )
    val nativeCurrency: NativeCurrency
)

fun UserWithNativeCurrency.getCurrencyCode() : String {
    return this.nativeCurrency.currencyCode
}

fun UserWithNativeCurrency.getCurrencySymbol() : String {
    return this.nativeCurrency.currencySymbol
}