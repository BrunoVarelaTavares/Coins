package com.btavares.feature_database.data.entities

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class UserWithCryptocurrency(
    @Embedded val user: User,
    @Relation(
        parentColumn = "id",
        entityColumn = "cryptocurrencyId",
        associateBy = Junction(UserCryptocurrency::class)
    )
    val cryptocurrencies: List<Cryptocurrency>
)