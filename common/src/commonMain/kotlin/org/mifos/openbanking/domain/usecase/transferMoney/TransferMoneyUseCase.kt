package org.mifos.openbanking.domain.usecase.transferMoney

import org.mifos.openbanking.base.Response
import org.mifos.openbanking.data.repository.OpenBankingRepository
import org.mifos.openbanking.domain.usecase.base.BaseUseCase

class TransferMoneyUseCase(private val repository: OpenBankingRepository) :
    BaseUseCase<TransferMoneyRequest, TransferMoneyResponse>() {

    override suspend fun run(): Response<TransferMoneyResponse> {
        return repository.transferMoney(request)
    }
}