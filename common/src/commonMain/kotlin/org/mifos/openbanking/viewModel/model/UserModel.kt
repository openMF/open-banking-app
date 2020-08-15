package org.mifos.openbanking.viewModel.model

import kotlinx.serialization.Serializable

@Serializable
class UserModel {
    var token: String
    var username: String
    lateinit var banksConnected: Set<String>
    lateinit var accounts: List<AccountModel>

    constructor(
        token: String,
        username: String
    ) {
        this.token = token
        this.username = username
    }
}

@Serializable
class AccountModel(
    val accountId: String,
    val bankName: String,
    val bankId: String,
    var balance: Double? = null,
    var currency: String? = null
)