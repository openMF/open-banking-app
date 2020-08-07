package org.mifos.openbanking.viewModel.app

import dev.icerock.moko.mvvm.livedata.MutableLiveData
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import org.kodein.di.erased.instance
import org.mifos.openbanking.base.Response
import org.mifos.openbanking.coroutines.launchSilent
import org.mifos.openbanking.data.datasources.disk.DiskDataSource
import org.mifos.openbanking.di.KodeinInjector
import org.mifos.openbanking.domain.usecase.fetchBanks.FetchBanksRequest
import org.mifos.openbanking.domain.usecase.fetchBanks.FetchBanksUseCase
import kotlin.coroutines.CoroutineContext

object App {

    // LIVE DATA
    val supportedBanksLiveData = MutableLiveData<SupportedBanksState>(
        LoadingSupportedBanksState
    )

    // USE-CASES
    private val fetchBanksUseCase by KodeinInjector.instance<FetchBanksUseCase>()

    // ASYNC - COROUTINES
    private val coroutineContext by KodeinInjector.instance<CoroutineContext>()
    private val job: Job = Job()
    private val exceptionHandler = CoroutineExceptionHandler { _, _ -> }

    private val diskDataSource by KodeinInjector.instance<DiskDataSource>()

    fun appLaunch() {
        fetchBanks()
    }

    private fun fetchBanks() = launchSilent(
        coroutineContext,
        exceptionHandler,
        job
    ) {
        val response = fetchBanksUseCase.execute(FetchBanksRequest())
        if (response is Response.Success) {
            val bankList = response.data.bankList
            val sortedBy = bankList.sortedBy { it.shortName }
            diskDataSource.saveSupportedBanks(sortedBy)
            supportedBanksLiveData.postValue(SuccessSupportedBanksState)
        } else if (response is Response.Error) {
            supportedBanksLiveData.postValue(ErrorSupportedBanksState(response.message))
        }
    }
}