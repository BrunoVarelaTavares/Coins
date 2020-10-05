package com.btavares.feature_database.data.entities

import androidx.room.Embedded
import androidx.room.Relation

data class UserPortfolio(
    @Embedded val user: User,
    @Relation(
        parentColumn = "id",
        entityColumn = "user_id")

    val portfolioBalance: PortfolioBalance
)