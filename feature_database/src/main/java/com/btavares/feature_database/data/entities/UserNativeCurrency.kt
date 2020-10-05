package com.btavares.feature_database.data.entities

import androidx.room.Entity

@Entity(primaryKeys = ["id", "currencyCode"])
data class UserNativeCurrency(
    val id: Long,
    val currencyCode: String
)