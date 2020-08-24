package org.mifos.openbanking.data.datasources.network

import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.http.ContentType
import io.ktor.http.content.*
import kotlinx.serialization.json.Json
import org.mifos.openbanking.base.Response
import org.mifos.openbanking.domain.usecase.createTransactionRequest.CreateTransactionRequestRequest
import org.mifos.openbanking.domain.usecase.createTransactionRequest.CreateTransactionRequestResponse
import org.mifos.openbanking.domain.usecase.fetchTransactionById.FetchTransactionByIdRequest
import org.mifos.openbanking.domain.usecase.fetchTransactionById.FetchTransactionByIdResponse
import org.mifos.openbanking.domain.usecase.fetchTransactionById.Transaction
import org.mifos.openbanking.domain.usecase.fetchTransactionRequests.FetchTransactionRequestsRequest
import org.mifos.openbanking.domain.usecase.fetchTransactionRequests.FetchTransactionRequestsResponse
import org.mifos.openbanking.domain.usecase.fetchTransactions.FetchTransactionsRequest
import org.mifos.openbanking.domain.usecase.fetchTransactions.FetchTransactionsResponse

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

    suspend fun fetchTransactionById(request: FetchTransactionByIdRequest): Response<FetchTransactionByIdResponse> {
        try {
            val response = client.get<String>(
                API_HOST + fetchTransactionByIdPath(
                    request.bankId,
                    request.accountId,
                    request.transactionId
                )
            ) {
                headers {
                    append("Authorization", "DirectLogin token=${request.token}")
                }
            }

            val transaction =
                Json.nonstrict.parse(Transaction.serializer(), response)
            return Response.Success(FetchTransactionByIdResponse(transaction))

        } catch (exp: ClientRequestException) {
            return Response.Error(exp)
        } catch (exp: Exception) {
            return Response.Error(exp)
        }
    }

    suspend fun fetchTransactions(request: FetchTransactionsRequest): Response<FetchTransactionsResponse> {
        try {
            val response = client.get<String>(
                API_HOST + fetchTransactionsPath(
                    request.bankId,
                    request.accountId
                )
            ) {
                headers {
                    append("Authorization", "DirectLogin token=${request.token}")
                }
            }

            val fetchTransactionsResponse =
                Json.nonstrict.parse(FetchTransactionsResponse.serializer(), response)
            return Response.Success(fetchTransactionsResponse)

        } catch (exp: ClientRequestException) {
            return Response.Error(exp)
        } catch (exp: Exception) {
            return Response.Error(exp)
        }
    }

}