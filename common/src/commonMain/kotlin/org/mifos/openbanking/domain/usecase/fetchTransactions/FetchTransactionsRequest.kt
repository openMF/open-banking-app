package org.mifos.openbanking.domain.usecase.fetchTransactions

import org.mifos.openbanking.domain.usecase.base.BaseRequest


class FetchTransactionsRequest(
    val token: String,
    val bankId: String,
    val accountId: String
) :
    BaseRequest {

    override fun validate(): Boolean {
        return true
    }

}