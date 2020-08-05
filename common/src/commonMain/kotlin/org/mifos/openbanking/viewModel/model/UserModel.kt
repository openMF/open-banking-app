package org.mifos.openbanking.viewModel.model

import kotlinx.serialization.Serializable

@Serializable
class UserModel(
    var token: String? = null,
    var username: String? = null,
    var banksConnected: Set<String> = emptySet(),
    var accounts: List<AccountModel>? = null
)

@Serializable
class AccountModel(
    val accountId: String,
    val bankId: String,
    var balance: Double? = null,
    var currency: String? = null
)