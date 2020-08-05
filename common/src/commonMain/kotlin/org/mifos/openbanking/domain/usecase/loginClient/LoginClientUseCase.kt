package org.mifos.openbanking.domain.usecase.loginClient

import org.mifos.openbanking.base.Response
import org.mifos.openbanking.data.repository.OpenBankingRepository
import org.mifos.openbanking.domain.usecase.base.BaseUseCase

class LoginClientUseCase(private val repository: OpenBankingRepository) :
    BaseUseCase<LoginClientRequest, LoginClientResponse>() {

    override suspend fun run(): Response<LoginClientResponse> {
        return repository.loginClient(request)
    }
}