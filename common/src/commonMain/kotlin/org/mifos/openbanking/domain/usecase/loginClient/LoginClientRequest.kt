package org.mifos.openbanking.domain.usecase.loginClient

import org.mifos.openbanking.domain.usecase.base.BaseRequest

class LoginClientRequest(
    val username: String,
    val password: String,
    val consumer_key: String
) : BaseRequest {

    override fun validate(): Boolean {
        // TODO
        return true
    }

}