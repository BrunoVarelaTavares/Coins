package com.btavares.feature_database.data.entities

import androidx.room.*


@Entity(tableName = "portfolio")
data class Portfolio(

    @PrimaryKey(autoGenerate = true)
    val portfolioId: Long,

/*    @ColumnInfo(name = "balance_id")
    val balanceId: Long,

    @Embedded var cryptocurrency: Cryptocurrency?,*/

    @ColumnInfo(name = "native_currency_value")
    var nativeCurrencyValue: Double?,

    @ColumnInfo(name = "cryptocurrency_value")
    var cryptocurrencyValue: String?

)


@Entity(primaryKeys = ["portfolioId", "cryptocurrencyId"])
data class PortfolioCryptocurrency(
    val portfolioId: Long,
    val cryptocurrencyId: String
)


data class PortfolioWithCryptocurrency(
    @Embedded val portfolio: Portfolio,
    @Relation(
        parentColumn = "portfolioId",
        entityColumn = "cryptocurrencyId",
        associateBy = Junction(PortfolioCryptocurrency::class)
    )
    val cryptocurrencies: List<Cryptocurrency>
)
