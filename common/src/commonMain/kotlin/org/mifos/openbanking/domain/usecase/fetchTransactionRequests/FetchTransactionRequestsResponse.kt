package org.mifos.openbanking.domain.usecase.fetchTransactionRequests

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class FetchTransactionRequestsResponse(
    @SerialName("transaction_requests_with_charges") val transactionRequestsList: List<TransactionRequest>
)

@Serializable
data class TransactionRequest(
    val id: String,
    val type: String,
    val from: Account,
    val details: Details
) {
    @Serializable
    data class Account(
        @SerialName("bank_id") val bankId: String,
        @SerialName("account_id") val accountId: String
    )

    @Serializable
    data class Details(
        @SerialName("to_sandbox_tan")
        val to: Account,
        val value: Value,
        val description: String
    ) {
        @Serializable
        data class Value(val currency: String, val amount: Double)
    }
}