package org.mifos.openbanking.domain.usecase.fetchTransactions

import org.mifos.openbanking.base.Response
import org.mifos.openbanking.data.repository.OpenBankingRepository
import org.mifos.openbanking.domain.usecase.base.BaseUseCase

class FetchTransactionsUseCase(private val repository: OpenBankingRepository) :
    BaseUseCase<FetchTransactionsRequest, FetchTransactionsResponse>() {

    override suspend fun run(): Response<FetchTransactionsResponse> {
        return repository.fetchTransactions(request)
    }
}