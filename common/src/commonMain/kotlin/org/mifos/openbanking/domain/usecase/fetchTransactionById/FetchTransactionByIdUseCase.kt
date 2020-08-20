package org.mifos.openbanking.domain.usecase.fetchTransactionById

import org.mifos.openbanking.base.Response
import org.mifos.openbanking.data.repository.OpenBankingRepository
import org.mifos.openbanking.domain.usecase.base.BaseUseCase

class FetchTransactionByIdUseCase(private val repository: OpenBankingRepository) :
    BaseUseCase<FetchTransactionByIdRequest, FetchTransactionByIdResponse>() {

    override suspend fun run(): Response<FetchTransactionByIdResponse> {
        return repository.fetchTransactionById(request)
    }
}