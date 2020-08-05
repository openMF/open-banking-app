package org.mifos.openbanking.domain.usecase.createClient

import org.mifos.openbanking.base.Response
import org.mifos.openbanking.data.repository.OpenBankingRepository
import org.mifos.openbanking.domain.usecase.base.BaseUseCase

class CreateClientUseCase(private val repository: OpenBankingRepository) :
    BaseUseCase<CreateClientRequest, CreateClientResponse>() {

    override suspend fun run(): Response<CreateClientResponse> {
        return repository.createClient(request)
    }
}