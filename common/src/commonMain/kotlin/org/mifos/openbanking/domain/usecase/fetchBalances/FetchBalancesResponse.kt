package org.mifos.openbanking.domain.usecase.fetchBalances

class FetchBalancesResponse(val accountBalances: List<AccountBalance>)

class AccountBalance(
    val accountId: String,
    val currency: String,
    val amount: Double
)