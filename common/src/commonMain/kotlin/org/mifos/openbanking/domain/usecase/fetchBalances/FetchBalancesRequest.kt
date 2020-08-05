package org.mifos.openbanking.domain.usecase.fetchBalances

import org.mifos.openbanking.domain.usecase.base.BaseRequest

class FetchBalancesRequest(val bankId: String, val token: String?) :
    BaseRequest {

    override fun validate(): Boolean {
        return token != null
    }

}