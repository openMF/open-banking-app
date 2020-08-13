package org.mifos.openbanking.data.datasources.network

class NetworkDataSource(
    private val clientApi: ClientApi,
    private val banksApi: BanksApi,
    private val transactionApi: TransactionApi
) {

    fun getClientApi(): ClientApi {
        return clientApi
    }

    fun getBankApi(): BanksApi {
        return banksApi
    }

    fun getTransactionApi(): TransactionApi {
        return transactionApi
    }

}