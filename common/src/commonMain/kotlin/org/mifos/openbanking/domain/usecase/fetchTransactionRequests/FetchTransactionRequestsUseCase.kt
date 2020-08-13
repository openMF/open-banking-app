package org.mifos.openbanking.domain.usecase.fetchTransactionRequests

import org.mifos.openbanking.base.Response
import org.mifos.openbanking.data.repository.OpenBankingRepository
import org.mifos.openbanking.domain.usecase.base.BaseUseCase

class FetchTransactionRequestsUseCase(private val repository: OpenBankingRepository) :
    BaseUseCase<FetchTransactionRequestsRequest, FetchTransactionRequestsResponse>() {

    override suspend fun run(): Response<FetchTransactionRequestsResponse> {
        return repository.fetchTransactionRequests(request)
    }
}