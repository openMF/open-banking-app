package org.mifos.openbanking.di


import org.kodein.di.Kodein
import org.kodein.di.erased.bind
import org.kodein.di.erased.instance
import org.kodein.di.erased.provider
import org.kodein.di.erased.singleton
import org.mifos.openbanking.ApplicationDispatcher
import org.mifos.openbanking.data.datasources.disk.DiskDataSource
import org.mifos.openbanking.data.datasources.network.BanksApi
import org.mifos.openbanking.data.datasources.network.ClientApi
import org.mifos.openbanking.data.datasources.network.NetworkDataSource
import org.mifos.openbanking.data.repository.OpenBankingRepository
import org.mifos.openbanking.domain.usecase.createClient.CreateClientUseCase
import org.mifos.openbanking.domain.usecase.fetchAccounts.FetchAccountsUseCase
import org.mifos.openbanking.domain.usecase.fetchBalances.FetchBalancesUseCase
import org.mifos.openbanking.domain.usecase.fetchBanks.FetchBanksUseCase
import org.mifos.openbanking.domain.usecase.loginClient.LoginClientUseCase
import org.mifos.openbanking.domain.usecase.transferMoney.TransferMoneyUseCase
import kotlin.coroutines.CoroutineContext
import kotlin.native.concurrent.ThreadLocal

@ThreadLocal
val KodeinInjector = Kodein {

    bind<CoroutineContext>() with provider { ApplicationDispatcher }

    /**
     * USECASES
     */
    bind<CreateClientUseCase>() with singleton {
        CreateClientUseCase(
            instance()
        )
    }
    bind<LoginClientUseCase>() with singleton {
        LoginClientUseCase(
            instance()
        )
    }
    bind<FetchAccountsUseCase>() with singleton {
        FetchAccountsUseCase(
            instance()
        )
    }
    bind<FetchBalancesUseCase>() with singleton {
        FetchBalancesUseCase(
            instance()
        )
    }
    bind<FetchBanksUseCase>() with singleton {
        FetchBanksUseCase(
            instance()
        )
    }
    bind<TransferMoneyUseCase>() with singleton {
        TransferMoneyUseCase(
            instance()
        )
    }

    /**
     * REPOSITORIES
     */
    bind<OpenBankingRepository>() with provider {
        OpenBankingRepository(
            instance()
        )
    }

    /**
     * DATA SOURCES
     */
    bind<NetworkDataSource>() with singleton {
        NetworkDataSource(
            instance(),
            instance()
        )
    }

    bind<DiskDataSource>() with singleton { DiskDataSource() }


    /**
     * NETWORK API
     */
    bind<ClientApi>() with provider { ClientApi() }
    bind<BanksApi>() with provider { BanksApi() }
}