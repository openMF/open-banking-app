package org.mifos.openbanking.common.domain.usecase.fetchBalances

import org.mifos.openbanking.common.base.Response
import org.mifos.openbanking.common.data.repository.OpenBankingRepository
import org.mifos.openbanking.common.domain.usecase.base.BaseUseCase

class FetchBalancesUseCase(private val repository: OpenBankingRepository) : BaseUseCase<FetchBalancesRequest, FetchBalancesResponse>() {

    override suspend fun run(): Response<FetchBalancesResponse> {
        return repository.fetchBalances(request)
    }
}