package org.mifos.openbanking.domain.usecase.fetchTransactions

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
class FetchTransactionsResponse(
    @SerialName("transactions") val transactionsList: List<Transaction>
)

@Serializable
data class Transaction(
    val id: String,
    @SerialName("this_account")
    val thisAccount: ThisAccount,
    @SerialName("other_account")
    val otherAccount: OtherAccount,
    val details: Details
) {

    @Serializable
    data class ThisAccount(
        val id: String,
        @SerialName("bank_routing")
        val bankRouting : Routing,
        @SerialName("account_routings")
        val accountRoutings: List<Routing>,
        val holders: List<Holder>
    )

    @Serializable
    data class OtherAccount(
        val id: String,
        @SerialName("bank_routing")
        val bankRouting : Routing,
        @SerialName("account_routings")
        val accountRoutings: List<Routing>,
        val holder: Holder
    )

    @Serializable
    data class Routing(
        val scheme: String?,
        val address: String?
    )

    @Serializable
    data class Holder(
        val name: String,
        @SerialName("is_alias")
        val isAlias: Boolean
    )

    @Serializable
    data class Details(
        val type: String,
        val description: String,
        val posted: String,
        val completed: String,
        @SerialName("new_balance")
        val newBalance: Balance,
        val value: Balance
    ) {
        @Serializable
        data class Balance(val currency: String, val amount: Double)
    }

}