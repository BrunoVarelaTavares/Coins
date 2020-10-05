package com.btavares.feature_database.data.entities

import androidx.room.*

@Entity(tableName = "balance")
data class PortfolioBalance (
    @PrimaryKey(autoGenerate = true)
    val portfolioBalanceId : Long,

    @ColumnInfo(name = "balance")
    val total: Double?,

    @ColumnInfo(name = "user_id")
    val userId : Long

)


data class PortfolioBalanceWithPortfolioCurrencies(
    @Embedded val portfolioBalance: PortfolioBalance,

    @Relation(
        parentColumn = "portfolio_balance_id",
        entityColumn = "balance_id"
    )
    val currencies: List<PortfolioCurrency>

)


