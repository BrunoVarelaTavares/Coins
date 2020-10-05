package com.btavares.feature_database.data.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "portfolio")
data class PortfolioCurrency(

    @PrimaryKey(autoGenerate = true)
    val portfolioId: Long,

    @ColumnInfo(name = "balance_id")
    val balanceId: Long,

    @Embedded var cryptocurrency: Cryptocurrency?,

    val value: Double?



)