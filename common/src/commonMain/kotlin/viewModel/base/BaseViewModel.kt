package org.mifos.openbanking.common.viewModel.base

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import org.kodein.di.erased.instance
import org.mifos.openbanking.common.di.KodeinInjector
import org.mifos.openbanking.common.viewModel.model.UserModel
import kotlin.coroutines.CoroutineContext

open class BaseViewModel : ViewModel() {

    // MODEL
    val userModel by KodeinInjector.instance<UserModel>()

    // ASYNC - COROUTINES
    protected val coroutineContext by KodeinInjector.instance<CoroutineContext>()
    protected val job: Job = Job()
    protected val exceptionHandler = CoroutineExceptionHandler { _, _ -> }

}