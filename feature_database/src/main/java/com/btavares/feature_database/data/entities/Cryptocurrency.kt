package com.btavares.feature_database.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cryptocurrency")
data class Cryptocurrency(

    @PrimaryKey(autoGenerate = false)
    val cryptocurrencyId : String,

    @ColumnInfo(name = "coin_name")
    val name: String,

    @ColumnInfo(name = "image_url")
    val imageUrl: String,

    @ColumnInfo(name = "coin_symbol")
    val coinSymbol: String,

    @ColumnInfo(name = "coin_percentage")
    val coinPercentage: String,

    @ColumnInfo(name = "coin_description")
    val coinDescription: String

)