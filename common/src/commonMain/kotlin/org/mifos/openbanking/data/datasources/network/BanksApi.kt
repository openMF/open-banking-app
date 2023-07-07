package org.mifos.openbanking.data.datasources.network

import io.ktor.client.HttpClient
import io.ktor.client.features.ClientRequestException
import io.ktor.client.request.get
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import org.mifos.openbanking.base.Response
import org.mifos.openbanking.domain.usecase.fetchBanks.Bank
import org.mifos.openbanking.domain.usecase.fetchBanks.FetchBanksRequest
import org.mifos.openbanking.domain.usecase.fetchBanks.FetchBanksResponse

class BanksApi {

    private val client = HttpClient()

    suspend fun fetchBanks(request: FetchBanksRequest): Response<FetchBanksResponse> {
        return try {
            val response = client.get<String>(API_HOST + BANKS_PATH)

            val responseBanks = (Json.parseToJsonElement(response) as JsonObject)["banks"].toString()
            val bankList =
                Json.decodeFromString(ListSerializer(Bank.serializer()), responseBanks)

            Response.Success(
                FetchBanksResponse(
                    bankList
                )
            )

        } catch (exp: ClientRequestException) {
            Response.Error(exp)
        } catch (exp: Exception) {
            Response.Error(exp)
        }
    }
}