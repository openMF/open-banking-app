package org.mifos.openbanking.domain.usecase.fetchBanks

import org.mifos.openbanking.domain.usecase.base.BaseRequest


class FetchBanksRequest() :
    BaseRequest {

    override fun validate(): Boolean {
        return true
    }

}