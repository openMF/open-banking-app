package org.mifos.openbanking.common.viewModel.auth

import dev.icerock.moko.mvvm.livedata.MutableLiveData
import org.kodein.di.erased.instance
import org.mifos.openbanking.common.base.Response
import org.mifos.openbanking.common.data.datasources.network.CONSUMER_KEY
import org.mifos.openbanking.common.di.KodeinInjector
import org.mifos.openbanking.common.domain.usecase.fetchBanks.FetchBanksRequest
import org.mifos.openbanking.common.domain.usecase.fetchBanks.FetchBanksUseCase
import org.mifos.openbanking.common.domain.usecase.loginClient.LoginClientRequest
import org.mifos.openbanking.common.domain.usecase.loginClient.LoginClientUseCase
import org.mifos.openbanking.common.utils.coroutines.launchSilent
import org.mifos.openbanking.common.viewModel.base.BaseViewModel

class AuthViewModel : BaseViewModel() {

    // LIVE DATA
    val authStateLiveData = MutableLiveData<AuthState>(LoadingAuthState)

    // USE CASE
    private val loginClientUseCase by KodeinInjector.instance<LoginClientUseCase>()
    private val fetchBanksUseCase by KodeinInjector.instance<FetchBanksUseCase>()

    fun createClient() = launchSilent(coroutineContext, exceptionHandler, job) {

    }

    fun loginClient(username: String, password: String) =
        launchSilent(coroutineContext, exceptionHandler, job) {
            val request = LoginClientRequest(
                username,
                password,
                CONSUMER_KEY
            )

            val response = loginClientUseCase.execute(request)

            if (response is Response.Success) {
                userModel.username = username
                userModel.token = response.data.token
                authStateLiveData.postValue(SuccessAuthState)
            } else if (response is Response.Error) {
                authStateLiveData.postValue(ErrorAuthState(response.exception.message))
            }
        }

    fun appStart() = launchSilent(coroutineContext, exceptionHandler, job) {
        val response = fetchBanksUseCase.execute(FetchBanksRequest())
    }

}