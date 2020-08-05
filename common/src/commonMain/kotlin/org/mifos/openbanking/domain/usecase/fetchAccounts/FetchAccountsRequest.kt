package org.mifos.openbanking.domain.usecase.fetchAccounts

import org.mifos.openbanking.domain.usecase.base.BaseRequest

class FetchAccountsRequest(val token: String?) :
    BaseRequest {

    override fun validate(): Boolean {
        return token != null
    }

}