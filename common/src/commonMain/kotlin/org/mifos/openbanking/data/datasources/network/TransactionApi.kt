package org.mifos.openbanking.data.datasources.network

import io.ktor.client.HttpClient
import io.ktor.client.features.ClientRequestException
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.http.ContentType
import io.ktor.http.content.TextContent
import kotlinx.serialization.json.Json
import org.mifos.openbanking.base.Response
import org.mifos.openbanking.domain.usecase.createTransactionRequest.CreateTransactionRequestRequest
import org.mifos.openbanking.domain.usecase.createTransactionRequest.CreateTransactionRequestResponse
import org.mifos.openbanking.domain.usecase.fetchTransactionRequests.FetchTransactionRequestsRequest
import org.mifos.openbanking.domain.usecase.fetchTransactionRequests.FetchTransactionRequestsResponse

class TransactionApi {

    private val client = HttpClient()

    suspend fun createTransactionRequest(request: CreateTransactionRequestRequest): Response<CreateTransactionRequestResponse> {
        try {

            val content = "{" +
                    "  \"to\": {" +
                    "    \"bank_id\": \"${request.destinationBankId}\"," +
                    "    \"account_id\": \"${request.destinationAccountId}\"" +
                    "  }," +
                    "  \"value\": {" +
                    "    \"currency\": \"${request.currency}\"," +
                    "    \"amount\": \"${request.amount}\"" +
                    "  }," +
                    "  \"description\": \"${request.description}\"" +
                    "}"

            val response = client.post<String>(
                urlString = API_HOST + createTransactionRequestPath(
                    request.sourceBankId,
                    request.sourceAccountId
                )
            ) {
                headers {
                    append("Authorization", "DirectLogin token=${request.token}")
                }
                body = TextContent(content, contentType = ContentType.Application.Json)
            }

            return Response.Success(CreateTransactionRequestResponse())

        } catch (exp: ClientRequestException) {
            return Response.Error(exp)
        } catch (exp: Exception) {
            return Response.Error(exp)
        }
    }

    suspend fun fetchTransactionRequests(request: FetchTransactionRequestsRequest): Response<FetchTransactionRequestsResponse> {
        try {
            val response = client.get<String>(
                API_HOST + fetchTransactionRequestsPath(
                    request.bankId,
                    request.accountId
                )
            ) {
                headers {
                    append("Authorization", "DirectLogin token=${request.token}")
                }
            }

            val transactionRequestsList =
                Json.nonstrict.parse(FetchTransactionRequestsResponse.serializer(), response)
            return Response.Success(transactionRequestsList)

        } catch (exp: ClientRequestException) {
            return Response.Error(exp)
        } catch (exp: Exception) {
            return Response.Error(exp)
        }
    }

}