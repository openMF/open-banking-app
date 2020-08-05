package org.mifos.openbanking.data.datasources.network

class NetworkDataSource(
    private val clientApi: ClientApi,
    private val banksApi: BanksApi
) {

    fun getClientApi(): ClientApi {
        return clientApi
    }

    fun getBankApi(): BanksApi {
        return banksApi
    }

}