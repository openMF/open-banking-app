package org.mifos.openbanking

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import org.kodein.di.erased.instance
import org.mifos.openbanking.coroutines.launchSilent
import org.mifos.openbanking.data.datasources.disk.DiskDataSource
import org.mifos.openbanking.di.KodeinInjector
import org.mifos.openbanking.domain.usecase.fetchBanks.FetchBanksRequest
import org.mifos.openbanking.domain.usecase.fetchBanks.FetchBanksUseCase
import org.mifos.openbanking.livedata.UserLiveData
import kotlin.coroutines.CoroutineContext

object App {

    // LIVE DATA
    private val userLiveData by KodeinInjector.instance<UserLiveData>()

    // USE-CASES
    private val fetchBanksUseCase by KodeinInjector.instance<FetchBanksUseCase>()

    // ASYNC - COROUTINES
    private val coroutineContext by KodeinInjector.instance<CoroutineContext>()
    private val job: Job = Job()
    private val exceptionHandler = CoroutineExceptionHandler { _, _ -> }

    private val diskDataSource by KodeinInjector.instance<DiskDataSource>()

    fun appLaunch() {
        val userModel = diskDataSource.getUserModel()
        if (userModel != null) {
            userLiveData.updateUserModel(userModel)
        }
        fetchBanks()
    }

    private fun fetchBanks() = launchSilent(
        coroutineContext,
        exceptionHandler,
        job
    ) {
        val response = fetchBanksUseCase.execute(FetchBanksRequest())
    }
}