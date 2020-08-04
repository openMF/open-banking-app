package org.mifos.openbanking.common.domain.usecase.fetchBanks

import org.mifos.openbanking.common.base.Response
import org.mifos.openbanking.common.data.repository.OpenBankingRepository
import org.mifos.openbanking.common.domain.usecase.base.BaseUseCase

class FetchBanksUseCase(private val repository: OpenBankingRepository) :
    BaseUseCase<FetchBanksRequest, FetchBanksResponse>() {

    override suspend fun run(): Response<FetchBanksResponse> {
        return repository.fetchBanks(request)
    }
}