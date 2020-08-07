package org.mifos.openbanking.viewModel.transfer

import dev.icerock.moko.mvvm.livedata.MutableLiveData
import org.kodein.di.erased.instance
import org.mifos.openbanking.base.Response
import org.mifos.openbanking.coroutines.launchSilent
import org.mifos.openbanking.data.datasources.disk.DiskDataSource
import org.mifos.openbanking.di.KodeinInjector
import org.mifos.openbanking.domain.usecase.fetchBanks.Bank
import org.mifos.openbanking.domain.usecase.transferMoney.TransferMoneyRequest
import org.mifos.openbanking.domain.usecase.transferMoney.TransferMoneyUseCase
import org.mifos.openbanking.viewModel.base.BaseViewModel

class TransferViewModel : BaseViewModel() {

    // LIVE DATA
    val transferStateLiveData = MutableLiveData<TransferState>(
        LoadingTransferState
    )

    // USE CASE
    private val transferMoneyUseCase by KodeinInjector.instance<TransferMoneyUseCase>()

    private val diskDataSource by KodeinInjector.instance<DiskDataSource>()

    fun transferMoney(
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
            TransferMoneyRequest(
                diskDataSource.getUserModel()!!.token!!,
                sourceBankId,
                sourceAccountId,
                destinationBankId,
                destinationAccountId,
                currency,
                amount,
                description
            )

        val response = transferMoneyUseCase.execute(request)
        if (response is Response.Success) {
            transferStateLiveData.postValue(SuccessTransferState)
        } else if (response is Response.Error) {
            transferStateLiveData.postValue(ErrorTransferState(response.message))
        }
    }

    fun getSupportedBanks(): List<Bank> {
        return diskDataSource.getSupportedBanks()
    }
}