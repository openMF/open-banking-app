package org.mifos.openbanking.viewModel.transaction

import dev.icerock.moko.mvvm.livedata.MutableLiveData
import org.kodein.di.erased.instance
import org.mifos.openbanking.base.Response
import org.mifos.openbanking.coroutines.launchSilent
import org.mifos.openbanking.data.datasources.disk.DiskDataSource
import org.mifos.openbanking.di.KodeinInjector
import org.mifos.openbanking.domain.usecase.createTransactionRequest.CreateTransactionRequestRequest
import org.mifos.openbanking.domain.usecase.createTransactionRequest.CreateTransactionRequestUseCase
import org.mifos.openbanking.domain.usecase.fetchBanks.Bank
import org.mifos.openbanking.domain.usecase.fetchTransactionRequests.FetchTransactionRequestsRequest
import org.mifos.openbanking.domain.usecase.fetchTransactionRequests.FetchTransactionRequestsUseCase
import org.mifos.openbanking.viewModel.base.BaseViewModel
import org.mifos.openbanking.viewModel.model.TransactionRequestModel

class TransactionViewModel : BaseViewModel() {

    // LIVE DATA
    val createTransactionRequestStateLiveData = MutableLiveData<CreateTransactionRequestState>(
        LoadingCreateTransactionRequestState
    )
    val fetchTransactionStateLiveData = MutableLiveData<FetchTransactionState>(
        LoadingFetchTransactionState
    )

    // USE CASE
    private val createTransactionRequestUseCase by KodeinInjector.instance<CreateTransactionRequestUseCase>()
    private val fetchTransactionRequestsUseCase by KodeinInjector.instance<FetchTransactionRequestsUseCase>()

    private val diskDataSource by KodeinInjector.instance<DiskDataSource>()

    fun fetchTransactionRequestsFor(bankId: String, accountId: String) = launchSilent(
        coroutineContext,
        exceptionHandler,
        job
    ) {
        val userModel = diskDataSource.getUserModel()
        val request = FetchTransactionRequestsRequest(
            userModel.token,
            bankId,
            accountId
        )
        val response = fetchTransactionRequestsUseCase.execute(request)
        if (response is Response.Success) {
            val transactionModelList = response.data.transactionRequestsList
                .map {
                    TransactionRequestModel(
                        it.id,
                        it.type,
                        it.from.bankId,
                        it.from.accountId,
                        it.details.to.bankId,
                        it.details.to.accountId,
                        it.details.value.currency,
                        it.details.value.amount,
                        it.details.description
                    )
                }
            fetchTransactionStateLiveData.postValue(
                SuccessFetchTransactionState(
                    transactionModelList
                )
            )
        } else if (response is Response.Error) {
            fetchTransactionStateLiveData.postValue(ErrorFetchTransactionState(response.message))
        }
    }

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
                diskDataSource.getUserModel()!!.token,
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
            createTransactionRequestStateLiveData.postValue(
                ErrorCreateTransactionRequestState(
                    response.message
                )
            )
        }
    }

    fun getSupportedBanks(): List<Bank> {
        return diskDataSource.getSupportedBanks()
    }
}