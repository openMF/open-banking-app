package org.mifos.openbanking.common.data.datasources.network

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