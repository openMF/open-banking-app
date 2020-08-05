package org.mifos.openbanking.domain.usecase.loginClient

import kotlinx.serialization.Serializable

@Serializable
class LoginClientResponse(val token: String)