package org.mifos.openbanking.common.domain.usecase.fetchBanks

import org.mifos.openbanking.common.domain.usecase.base.BaseRequest


class FetchBanksRequest() : BaseRequest {

    override fun validate(): Boolean {
        return true
    }

}