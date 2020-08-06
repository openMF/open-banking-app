package org.mifos.openbanking.viewModel.auth

import dev.icerock.moko.mvvm.livedata.MutableLiveData
import org.kodein.di.erased.instance
import org.mifos.openbanking.base.Response
import org.mifos.openbanking.coroutines.launchSilent
import org.mifos.openbanking.data.datasources.disk.DiskDataSource
import org.mifos.openbanking.data.datasources.network.CONSUMER_KEY
import org.mifos.openbanking.di.KodeinInjector
import org.mifos.openbanking.domain.usecase.loginClient.LoginClientRequest
import org.mifos.openbanking.domain.usecase.loginClient.LoginClientUseCase
import org.mifos.openbanking.viewModel.base.BaseViewModel
import org.mifos.openbanking.viewModel.model.UserModel

class AuthViewModel : BaseViewModel() {

    // LIVE DATA
    val authStateLiveData = MutableLiveData<AuthState>(
        LoadingAuthState
    )

    // USE CASE
    private val loginClientUseCase by KodeinInjector.instance<LoginClientUseCase>()

    private val diskDataSource by KodeinInjector.instance<DiskDataSource>()

    fun createClient() = launchSilent(
        coroutineContext,
        exceptionHandler,
        job
    ) {

    }

    fun loginClient(username: String, password: String) =
        launchSilent(
            coroutineContext,
            exceptionHandler,
            job
        ) {
            val request =
                LoginClientRequest(
                    username,
                    password,
                    CONSUMER_KEY
                )
            val response = loginClientUseCase.execute(request)

            if (response is Response.Success) {
                val userModel = UserModel()
                userModel.username = username
                userModel.token = response.data.token
                diskDataSource.saveUserModel(userModel)
                authStateLiveData.postValue(SuccessAuthState)
            } else if (response is Response.Error) {
                authStateLiveData.postValue(ErrorAuthState(response.exception.message))
            }
        }

    fun isUserLoggedIn(): Boolean {
        return diskDataSource.isUserLoggedIn()
    }

}