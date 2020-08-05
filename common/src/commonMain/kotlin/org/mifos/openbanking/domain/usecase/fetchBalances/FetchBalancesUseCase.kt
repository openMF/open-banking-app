package org.mifos.openbanking.domain.usecase.fetchBalances

import org.mifos.openbanking.base.Response
import org.mifos.openbanking.data.repository.OpenBankingRepository
import org.mifos.openbanking.domain.usecase.base.BaseUseCase

class FetchBalancesUseCase(private val repository: OpenBankingRepository) :
    BaseUseCase<FetchBalancesRequest, FetchBalancesResponse>() {

    override suspend fun run(): Response<FetchBalancesResponse> {
        return repository.fetchBalances(request)
    }
}