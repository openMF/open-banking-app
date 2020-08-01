package org.mifos.openbanking.common.viewModel.model

class UserModel(
    var token: String? = null,
    var username: String? = null,
    var banksConnected: Set<String> = emptySet(),
    var accounts: List<AccountModel>? = null
)

class AccountModel(
    val accountId: String,
    val bankId: String,
    var balance: Double? = null,
    var currency: String? = null
)