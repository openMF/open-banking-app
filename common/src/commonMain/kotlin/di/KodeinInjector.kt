package org.mifos.openbanking.common.di


import domain.usecase.createClient.CreateClientUseCase
import org.kodein.di.Kodein
import org.kodein.di.erased.bind
import org.kodein.di.erased.instance
import org.kodein.di.erased.provider
import org.kodein.di.erased.singleton
import org.mifos.openbanking.common.ApplicationDispatcher
import org.mifos.openbanking.common.data.datasources.network.BanksApi
import org.mifos.openbanking.common.data.datasources.network.ClientApi
import org.mifos.openbanking.common.data.datasources.network.NetworkDataSource
import org.mifos.openbanking.common.data.repository.OpenBankingRepository
import org.mifos.openbanking.common.domain.usecase.fetchAccounts.FetchAccountsUseCase
import org.mifos.openbanking.common.domain.usecase.fetchBalances.FetchBalancesUseCase
import org.mifos.openbanking.common.domain.usecase.fetchBanks.FetchBanksUseCase
import org.mifos.openbanking.common.domain.usecase.loginClient.LoginClientUseCase
import org.mifos.openbanking.common.viewModel.model.UserModel
import kotlin.coroutines.CoroutineContext
import kotlin.native.concurrent.ThreadLocal

@ThreadLocal
val KodeinInjector = Kodein {

    bind<CoroutineContext>() with provider { ApplicationDispatcher }

    /**
     * MODEL
     */
    bind<UserModel>() with singleton { UserModel() }

    /**
     * USECASES
     */
    bind<CreateClientUseCase>() with singleton { CreateClientUseCase(instance()) }
    bind<LoginClientUseCase>() with singleton { LoginClientUseCase(instance()) }
    bind<FetchAccountsUseCase>() with singleton { FetchAccountsUseCase(instance()) }
    bind<FetchBalancesUseCase>() with singleton { FetchBalancesUseCase(instance()) }
    bind<FetchBanksUseCase>() with singleton { FetchBanksUseCase(instance()) }

    /**
     * REPOSITORIES
     */
    bind<OpenBankingRepository>() with provider { OpenBankingRepository(instance()) }

    /**
     * REPOSITORY MANAGER
     */
    bind<NetworkDataSource>() with provider { NetworkDataSource(instance(), instance()) }

    /**
     * NETWORK API
     */
    bind<ClientApi>() with provider { ClientApi() }
    bind<BanksApi>() with provider { BanksApi() }
}