package org.mifos.openbanking.domain.usecase.fetchCards

import org.mifos.openbanking.domain.usecase.base.BaseRequest


class FetchCardsRequest(
    val token: String
) :
    BaseRequest {

    override fun validate(): Boolean {
        return true
    }

}