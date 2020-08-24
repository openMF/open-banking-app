package org.mifos.openbanking.di


import org.kodein.di.Kodein
import org.kodein.di.erased.bind
import org.kodein.di.erased.instance
import org.kodein.di.erased.provider
import org.kodein.di.erased.singleton
import org.mifos.openbanking.ApplicationDispatcher
import org.mifos.openbanking.data.datasources.disk.DiskDataSource
import org.mifos.openbanking.data.datasources.network.*
import org.mifos.openbanking.data.repository.OpenBankingRepository
import org.mifos.openbanking.domain.usecase.createClient.CreateClientUseCase
import org.mifos.openbanking.domain.usecase.createTransactionRequest.CreateTransactionRequestUseCase
import org.mifos.openbanking.domain.usecase.fetchAccounts.FetchAccountsUseCase
import org.mifos.openbanking.domain.usecase.fetchBalances.FetchBalancesUseCase
import org.mifos.openbanking.domain.usecase.fetchBanks.FetchBanksUseCase
import org.mifos.openbanking.domain.usecase.fetchCards.FetchCardsUseCase
import org.mifos.openbanking.domain.usecase.fetchTransactionById.FetchTransactionByIdUseCase
import org.mifos.openbanking.domain.usecase.fetchTransactionRequests.FetchTransactionRequestsUseCase
import org.mifos.openbanking.domain.usecase.fetchTransactions.FetchTransactionsUseCase
import org.mifos.openbanking.domain.usecase.loginClient.LoginClientUseCase
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
    bind<CreateTransactionRequestUseCase>() with singleton {
        CreateTransactionRequestUseCase(
            instance()
        )
    }
    bind<FetchTransactionRequestsUseCase>() with singleton {
        FetchTransactionRequestsUseCase(
            instance()
        )
    }
    bind<FetchCardsUseCase>() with singleton {
        FetchCardsUseCase(
            instance()
        )
    }
    bind<FetchTransactionByIdUseCase>() with singleton {
        FetchTransactionByIdUseCase(
            instance()
        )
    }
    bind<FetchTransactionsUseCase>() with singleton {
        FetchTransactionsUseCase(
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
            instance(),
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
    bind<TransactionApi>() with provider { TransactionApi() }
    bind<CardApi>() with provider { CardApi() }

}