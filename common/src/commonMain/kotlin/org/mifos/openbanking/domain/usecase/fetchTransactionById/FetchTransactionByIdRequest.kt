package org.mifos.openbanking.domain.usecase.fetchTransactionById

import org.mifos.openbanking.domain.usecase.base.BaseRequest


class FetchTransactionByIdRequest(
    val token: String,
    val bankId: String,
    val accountId: String,
    val transactionId: String
) :
    BaseRequest {

    override fun validate(): Boolean {
        return true
    }

}