package com.btavares.feature_database.data.entities

import androidx.room.*

@Entity(tableName = "balance")
data class Balance (
    @PrimaryKey(autoGenerate = true)
    val balanceId : Long,

    val totalBalance: Double?,

    @ColumnInfo(name = "user_id")
    val userId : Long

)

@Entity(primaryKeys = ["balanceId", "portfolioId"])
data class BalancePortfolio(
    val balanceId: Long,
    val portfolioId: Long
)

data class UserWithPortfolio(
    @Embedded val balance: Balance,
    @Relation(
        parentColumn = "balanceId",
        entityColumn = "portfolioId",
        associateBy = Junction(BalancePortfolio::class)
    )
    val portfolio: Portfolio
)


