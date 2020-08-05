package org.mifos.openbanking.viewModel.base

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import org.kodein.di.erased.instance
import org.mifos.openbanking.di.KodeinInjector
import org.mifos.openbanking.livedata.UserLiveData
import kotlin.coroutines.CoroutineContext

open class BaseViewModel : ViewModel() {

    // LIVE DATA
    protected val userLiveData by KodeinInjector.instance<UserLiveData>()

    // ASYNC - COROUTINES
    protected val coroutineContext by KodeinInjector.instance<CoroutineContext>()
    protected val job: Job = Job()
    protected val exceptionHandler = CoroutineExceptionHandler { _, _ -> }

}