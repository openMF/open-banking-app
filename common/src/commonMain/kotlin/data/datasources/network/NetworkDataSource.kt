package org.mifos.openbanking.common.data.datasources.network

class NetworkDataSource(
        private val clientApi: ClientApi
) {

    fun getClientApi(): ClientApi {
        return clientApi;
    }

}