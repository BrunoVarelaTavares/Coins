package com.btavares.feature_database.data.entities

import androidx.room.Entity

@Entity(primaryKeys = ["id", "cryptocurrencyId"])
data class UserCryptocurrency(
    val id: Long,
    val cryptocurrencyId: String
)
