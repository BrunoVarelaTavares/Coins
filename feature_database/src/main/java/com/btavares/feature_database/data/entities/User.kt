package com.btavares.feature_database.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val name: String,
    val email: String?
)




