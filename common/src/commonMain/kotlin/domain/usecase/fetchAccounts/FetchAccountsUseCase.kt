package org.mifos.openbanking.common.domain.usecase.fetchAccounts

import org.mifos.openbanking.common.base.Response
import org.mifos.openbanking.common.data.repository.OpenBankingRepository
import org.mifos.openbanking.common.domain.usecase.base.BaseUseCase

class FetchAccountsUseCase(private val repository: OpenBankingRepository) : BaseUseCase<FetchAccountsRequest, FetchAccountsResponse>() {

    override suspend fun run(): Response<FetchAccountsResponse> {
        return repository.fetchAccounts(request)
    }
}