package org.mifos.openbanking.common.domain.usecase.loginClient

import org.mifos.openbanking.common.base.Response
import org.mifos.openbanking.common.data.repository.OpenBankingRepository
import org.mifos.openbanking.common.domain.usecase.base.BaseUseCase

class LoginClientUseCase(private val repository: OpenBankingRepository) : BaseUseCase<LoginClientRequest, LoginClientResponse>() {

    override suspend fun run(): Response<LoginClientResponse> {
        return repository.loginClient(request)
    }
}