package org.mifos.openbanking.common.domain.usecase.fetchBalances

import org.mifos.openbanking.common.domain.usecase.base.BaseRequest

class FetchBalancesRequest(val bankId: String, val token: String?) : BaseRequest {

    override fun validate(): Boolean {
        return token != null
    }

}