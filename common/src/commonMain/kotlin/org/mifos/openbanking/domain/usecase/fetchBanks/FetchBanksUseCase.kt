package org.mifos.openbanking.domain.usecase.fetchBanks

import org.mifos.openbanking.base.Response
import org.mifos.openbanking.data.repository.OpenBankingRepository
import org.mifos.openbanking.domain.usecase.base.BaseUseCase

class FetchBanksUseCase(private val repository: OpenBankingRepository) :
    BaseUseCase<FetchBanksRequest, FetchBanksResponse>() {

    override suspend fun run(): Response<FetchBanksResponse> {
        return repository.fetchBanks(request)
    }
}