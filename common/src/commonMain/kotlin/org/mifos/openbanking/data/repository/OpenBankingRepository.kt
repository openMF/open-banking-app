package org.mifos.openbanking.data.repository

import org.mifos.openbanking.base.Response
import org.mifos.openbanking.data.datasources.network.NetworkDataSource
import org.mifos.openbanking.domain.usecase.createClient.CreateClientRequest
import org.mifos.openbanking.domain.usecase.createClient.CreateClientResponse
import org.mifos.openbanking.domain.usecase.createTransactionRequest.CreateTransactionRequestRequest
import org.mifos.openbanking.domain.usecase.createTransactionRequest.CreateTransactionRequestResponse
import org.mifos.openbanking.domain.usecase.fetchAccounts.FetchAccountsRequest
import org.mifos.openbanking.domain.usecase.fetchAccounts.FetchAccountsResponse
import org.mifos.openbanking.domain.usecase.fetchBalances.FetchBalancesRequest
import org.mifos.openbanking.domain.usecase.fetchBalances.FetchBalancesResponse
import org.mifos.openbanking.domain.usecase.fetchBanks.FetchBanksRequest
import org.mifos.openbanking.domain.usecase.fetchBanks.FetchBanksResponse
import org.mifos.openbanking.domain.usecase.fetchCards.FetchCardsRequest
import org.mifos.openbanking.domain.usecase.fetchCards.FetchCardsResponse
import org.mifos.openbanking.domain.usecase.fetchTransactionById.FetchTransactionByIdRequest
import org.mifos.openbanking.domain.usecase.fetchTransactionById.FetchTransactionByIdResponse
import org.mifos.openbanking.domain.usecase.fetchTransactionRequests.FetchTransactionRequestsRequest
import org.mifos.openbanking.domain.usecase.fetchTransactionRequests.FetchTransactionRequestsResponse
import org.mifos.openbanking.domain.usecase.fetchTransactions.FetchTransactionsRequest
import org.mifos.openbanking.domain.usecase.fetchTransactions.FetchTransactionsResponse
import org.mifos.openbanking.domain.usecase.loginClient.LoginClientRequest
import org.mifos.openbanking.domain.usecase.loginClient.LoginClientResponse

class OpenBankingRepository(
    private val networkDataSource: NetworkDataSource
) {

    suspend fun createClient(request: CreateClientRequest): Response<CreateClientResponse> {
        return networkDataSource.getClientApi()
            .createClient(request.name, request.contact, request.scopes, request.redirectUris);
    }

    suspend fun loginClient(request: LoginClientRequest): Response<LoginClientResponse> {
        return networkDataSource.getClientApi().loginClient(request);
    }

    suspend fun fetchAccounts(request: FetchAccountsRequest): Response<FetchAccountsResponse> {
        return networkDataSource.getClientApi().fetchAccounts(request)
    }

    suspend fun fetchBalances(request: FetchBalancesRequest): Response<FetchBalancesResponse> {
        return networkDataSource.getClientApi().fetchBalances(request)
    }

    suspend fun fetchBanks(request: FetchBanksRequest): Response<FetchBanksResponse> {
        return networkDataSource.getBankApi().fetchBanks(request)
    }

    suspend fun createTransactionRequest(request: CreateTransactionRequestRequest): Response<CreateTransactionRequestResponse> {
        return networkDataSource.getTransactionApi().createTransactionRequest(request)
    }

    suspend fun fetchTransactionRequests(request: FetchTransactionRequestsRequest): Response<FetchTransactionRequestsResponse> {
        return networkDataSource.getTransactionApi().fetchTransactionRequests(request)
    }

    suspend fun fetchTransactionById(request: FetchTransactionByIdRequest): Response<FetchTransactionByIdResponse> {
        return networkDataSource.getTransactionApi().fetchTransactionById(request)
    }

    suspend fun fetchTransactions(request: FetchTransactionsRequest): Response<FetchTransactionsResponse> {
        return networkDataSource.getTransactionApi().fetchTransactions(request)
    }

    suspend fun fetchCards(request: FetchCardsRequest): Response<FetchCardsResponse> {
        return networkDataSource.getCardApi().fetchCards(request)
    }

}
