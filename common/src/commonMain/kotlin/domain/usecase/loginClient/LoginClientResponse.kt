package org.mifos.openbanking.common.domain.usecase.loginClient

import kotlinx.serialization.Serializable

@Serializable
class LoginClientResponse(val token: String)