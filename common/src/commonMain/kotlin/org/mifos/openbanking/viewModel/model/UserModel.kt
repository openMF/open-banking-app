package org.mifos.openbanking.viewModel.model

import kotlinx.serialization.Serializable

@Serializable
class UserModel(
    var token: String,
    var username: String
) {
    var banksConnected: Set<String>? = null
    var accounts: List<AccountModel>? = null
}

@Serializable
class AccountModel(
    val accountId: String,
    val bankName: String,
    val bankId: String,
    var balance: Double? = null,
    var currency: String? = null
)