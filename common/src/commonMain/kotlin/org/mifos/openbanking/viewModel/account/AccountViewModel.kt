package org.mifos.openbanking.viewModel.account

import dev.icerock.moko.mvvm.livedata.MutableLiveData
import org.kodein.di.erased.instance
import org.mifos.openbanking.base.Response
import org.mifos.openbanking.coroutines.launchSilent
import org.mifos.openbanking.data.datasources.disk.DiskDataSource
import org.mifos.openbanking.di.KodeinInjector
import org.mifos.openbanking.domain.usecase.fetchAccounts.FetchAccountsRequest
import org.mifos.openbanking.domain.usecase.fetchAccounts.FetchAccountsUseCase
import org.mifos.openbanking.domain.usecase.fetchBalances.FetchBalancesRequest
import org.mifos.openbanking.domain.usecase.fetchBalances.FetchBalancesUseCase
import org.mifos.openbanking.viewModel.base.BaseViewModel
import org.mifos.openbanking.viewModel.model.AccountModel

class AccountViewModel : BaseViewModel() {

    // LIVE DATA
    val accountStateLiveData = MutableLiveData<AccountState>(
        LoadingAccountState
    )

    // USE CASE
    private val fetchAccountsUseCase by KodeinInjector.instance<FetchAccountsUseCase>()
    private val fetchBalancesUseCase by KodeinInjector.instance<FetchBalancesUseCase>()

    private val diskDataSource by KodeinInjector.instance<DiskDataSource>()

    fun fetchAccounts() = launchSilent(
        coroutineContext,
        exceptionHandler,
        job
    ) {
        val request =
            FetchAccountsRequest(
                diskDataSource.getUserModel()!!.token
            )

        val response = fetchAccountsUseCase.execute(request)

        if (response is Response.Success) {
            val supportedBanks = diskDataSource.getSupportedBanks()
            val accountModelList: MutableList<AccountModel> =
                mutableListOf()
            val banksConnected: MutableSet<String> = mutableSetOf()
            for (account in response.data.accounts) {
                accountModelList.add(
                    AccountModel(
                        account.accountId,
                        supportedBanks!!.find { bank -> bank.id == account.bankId }!!.shortName!!,
                        account.bankId
                    )
                )
                banksConnected.add(account.bankId)
            }
            val userModel = diskDataSource.getUserModel()!!
            userModel.accounts = accountModelList
            userModel.banksConnected = banksConnected
            diskDataSource.saveUserModel(userModel)
            accountStateLiveData.postValue(
                SuccessAccountState(accountModelList)
            )
        } else if (response is Response.Error) {
            accountStateLiveData.postValue(
                ErrorAccountState(response.message)
            )
        }
    }

    fun fetchBalances() = launchSilent(
        coroutineContext,
        exceptionHandler,
        job
    ) {
        val userModel = diskDataSource.getUserModel()!!
        for (bankId in userModel.banksConnected) {
            val response =
                fetchBalancesUseCase.execute(
                    FetchBalancesRequest(
                        bankId,
                        userModel.token
                    )
                )
            if (response is Response.Success) {
                for (accountBalances in response.data.accountBalances) {
                    val accountModel = userModel.accounts?.find { accountModel ->
                        accountBalances.accountId == accountModel.accountId
                    }
                    if (accountModel != null) {
                        accountModel.balance = accountBalances.amount
                        accountModel.currency = accountBalances.currency
                    }
                }
            } else if (response is Response.Error) {
                accountStateLiveData.postValue(
                    ErrorAccountState(
                        response.message
                    )
                )
                return@launchSilent
            }
        }

        diskDataSource.saveUserModel(userModel)
        accountStateLiveData.postValue(
            SuccessAccountState(
                userModel.accounts!!,
                true
            )
        )
    }
}