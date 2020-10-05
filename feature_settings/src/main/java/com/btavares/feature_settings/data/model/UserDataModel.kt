package com.btavares.feature_settings.data.model

import com.btavares.feature_settings.domain.model.UserDomainModel

internal class UserDataModel(
    val name: String,
    val email: String?
)

internal fun UserDataModel.toDomainModel() : UserDomainModel {
    return UserDomainModel(
        name = this.name,
        email = this.email ?: ""
    )
}

