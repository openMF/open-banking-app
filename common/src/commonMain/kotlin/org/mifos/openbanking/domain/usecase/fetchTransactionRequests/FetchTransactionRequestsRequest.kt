package org.mifos.openbanking.domain.usecase.fetchTransactionRequests

import org.mifos.openbanking.domain.usecase.base.BaseRequest


class FetchTransactionRequestsRequest(
    val token: String,
    val bankId: String,
    val accountId: String
) :
    BaseRequest {

    override fun validate(): Boolean {
        return true
    }

}