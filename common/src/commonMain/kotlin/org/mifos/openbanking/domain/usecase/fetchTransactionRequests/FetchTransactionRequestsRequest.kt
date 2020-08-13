package org.mifos.openbanking.domain.usecase.fetchTransactionRequests

import org.mifos.openbanking.domain.usecase.base.BaseRequest


class FetchTransactionRequestsRequest :
    BaseRequest {

    override fun validate(): Boolean {
        return true
    }

}