package org.mifos.openbanking.domain.usecase.fetchCards

import org.mifos.openbanking.base.Response
import org.mifos.openbanking.data.repository.OpenBankingRepository
import org.mifos.openbanking.domain.usecase.base.BaseUseCase

class FetchCardsUseCase(private val repository: OpenBankingRepository) :
    BaseUseCase<FetchCardsRequest, FetchCardsResponse>() {

    override suspend fun run(): Response<FetchCardsResponse> {
        return repository.fetchCards(request)
    }
}