package org.mifos.openbanking.common.viewModel.account

import dev.icerock.moko.mvvm.livedata.MutableLiveData
import org.kodein.di.erased.instance
import org.mifos.openbanking.common.base.Response
import org.mifos.openbanking.common.di.KodeinInjector
import org.mifos.openbanking.common.domain.usecase.fetchAccounts.FetchAccountsRequest
import org.mifos.openbanking.common.domain.usecase.fetchAccounts.FetchAccountsUseCase
import org.mifos.openbanking.common.domain.usecase.fetchBalances.FetchBalancesRequest
import org.mifos.openbanking.common.domain.usecase.fetchBalances.FetchBalancesUseCase
import org.mifos.openbanking.common.utils.coroutines.launchSilent
import org.mifos.openbanking.common.viewModel.base.BaseViewModel
import org.mifos.openbanking.common.viewModel.model.AccountModel

class AccountViewModel : BaseViewModel() {

    // LIVE DATA
    val accountStateLiveData = MutableLiveData<AccountState>(LoadingAccountState)

    // USE CASE
    private val fetchAccountsUseCase by KodeinInjector.instance<FetchAccountsUseCase>()
    private val fetchBalancesUseCase by KodeinInjector.instance<FetchBalancesUseCase>()

    fun fetchAccounts() = launchSilent(coroutineContext, exceptionHandler, job) {
        val request = FetchAccountsRequest(userModel.token)

        val response = fetchAccountsUseCase.execute(request)

        if (response is Response.Success) {
            val accountModelList: MutableList<AccountModel> = mutableListOf()
            for (account in response.data.accounts)
                accountModelList.add(AccountModel(account.accountId, account.bankId))
            userModel.accounts = accountModelList

            accountStateLiveData.postValue(SuccessAccountState(accountModelList))
        } else if (response is Response.Error) {
            accountStateLiveData.postValue(ErrorAccountState(response.message))
        }
    }

    fun fetchBalances() = launchSilent(coroutineContext, exceptionHandler, job) {
        for (account in userModel.accounts!!) {
            val response =
                fetchBalancesUseCase.execute(FetchBalancesRequest(account.bankId, userModel.token))
            if (response is Response.Success) {
                for (accountBalances in response.data.accountBalances) {
                    if (account.accountId == accountBalances.accountId) {
                        account.balance = accountBalances.amount
                        account.currency = accountBalances.currency
                    }
                }
            } else if (response is Response.Error) {
                accountStateLiveData.postValue(ErrorAccountState(response.message))
                return@launchSilent
            }
        }

        accountStateLiveData.postValue(SuccessAccountState(userModel.accounts!!, true))
    }

}