package domain.usecase.createClient

import org.mifos.openbanking.common.base.Response
import org.mifos.openbanking.common.data.repository.OpenBankingRepository
import org.mifos.openbanking.common.domain.usecase.base.BaseUseCase

class CreateClientUseCase(private val repository: OpenBankingRepository) : BaseUseCase<CreateClientRequest, CreateClientResponse>() {

    override suspend fun run(): Response<CreateClientResponse> {
        return repository.createClient(request)
    }
}