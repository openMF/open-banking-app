package org.mifos.openbanking.viewModel.transaction

import dev.icerock.moko.mvvm.livedata.MutableLiveData
import org.kodein.di.erased.instance
import org.mifos.openbanking.base.Response
import org.mifos.openbanking.coroutines.launchSilent
import org.mifos.openbanking.data.datasources.disk.DiskDataSource
import org.mifos.openbanking.di.KodeinInjector
import org.mifos.openbanking.domain.usecase.fetchBanks.Bank
import org.mifos.openbanking.domain.usecase.createTransactionRequest.CreateTransactionRequestRequest
import org.mifos.openbanking.domain.usecase.createTransactionRequest.CreateTransactionRequestUseCase
import org.mifos.openbanking.viewModel.base.BaseViewModel

class CreateTransactionRequestViewModel : BaseViewModel() {

    // LIVE DATA
    val createTransactionRequestStateLiveData = MutableLiveData<CreateTransactionRequestState>(
        LoadingCreateTransactionRequestState
    )

    // USE CASE
    private val createTransactionRequestUseCase by KodeinInjector.instance<CreateTransactionRequestUseCase>()

    private val diskDataSource by KodeinInjector.instance<DiskDataSource>()

    fun createTransactionRequest(
        sourceBankId: String,
        sourceAccountId: String,
        destinationBankId: String,
        destinationAccountId: String,
        currency: String,
        amount: Double,
        description: String
    ) = launchSilent(
        coroutineContext,
        exceptionHandler,
        job
    ) {
        val request =
            CreateTransactionRequestRequest(
                diskDataSource.getUserModel()!!.token!!,
                sourceBankId,
                sourceAccountId,
                destinationBankId,
                destinationAccountId,
                currency,
                amount,
                description
            )

        val response = createTransactionRequestUseCase.execute(request)
        if (response is Response.Success) {
            createTransactionRequestStateLiveData.postValue(SuccessCreateTransactionRequestState)
        } else if (response is Response.Error) {
            createTransactionRequestStateLiveData.postValue(ErrorCreateTransactionRequestState(response.message))
        }
    }

    fun getSupportedBanks(): List<Bank> {
        return diskDataSource.getSupportedBanks()
    }
}