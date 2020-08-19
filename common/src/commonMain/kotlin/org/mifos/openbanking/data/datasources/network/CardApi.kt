package org.mifos.openbanking.data.datasources.network

import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import kotlinx.serialization.json.Json
import org.mifos.openbanking.base.Response
import org.mifos.openbanking.domain.usecase.fetchCards.FetchCardsRequest
import org.mifos.openbanking.domain.usecase.fetchCards.FetchCardsResponse

class CardApi {

    private val client = HttpClient()

    suspend fun fetchCards(request: FetchCardsRequest): Response<FetchCardsResponse> {
        try {
            val response = client.get<String>(
                API_HOST + CARDS_PATH
            ) {
                headers {
                    append("Authorization", "DirectLogin token=${request.token}")
                }
            }

            val fetchCardsResponse = Json.nonstrict.parse(FetchCardsResponse.serializer(), response)
            return Response.Success(fetchCardsResponse)

        } catch (exp: ClientRequestException) {
            return Response.Error(exp)
        } catch (exp: Exception) {
            return Response.Error(exp)
        }
    }

}