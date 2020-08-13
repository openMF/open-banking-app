package org.mifos.openbanking.domain.usecase.fetchTransactionRequests

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

class FetchTransactionRequestsResponse(val transactionRequestsList: List<TransactionRequest>)

@Serializable
class TransactionRequest(
    val id: String,
    @SerialName("short_name")
    val shortName: String?,
    @SerialName("full_name")
    val fullName: String?,
    @SerialName("logo")
    val logoUrl: String?,
    @SerialName("website")
    val websiteUrl: String?
)