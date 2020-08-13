package org.mifos.openbanking.data.datasources.network

import io.ktor.client.HttpClient
import io.ktor.client.features.ClientRequestException
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.http.ContentType
import io.ktor.http.content.TextContent
import kotlinx.serialization.builtins.list
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.content
import org.mifos.openbanking.base.Response
import org.mifos.openbanking.domain.usecase.createClient.CreateClientResponse
import org.mifos.openbanking.domain.usecase.fetchAccounts.Account
import org.mifos.openbanking.domain.usecase.fetchAccounts.FetchAccountsRequest
import org.mifos.openbanking.domain.usecase.fetchAccounts.FetchAccountsResponse
import org.mifos.openbanking.domain.usecase.fetchBalances.AccountBalance
import org.mifos.openbanking.domain.usecase.fetchBalances.FetchBalancesRequest
import org.mifos.openbanking.domain.usecase.fetchBalances.FetchBalancesResponse
import org.mifos.openbanking.domain.usecase.loginClient.LoginClientRequest
import org.mifos.openbanking.domain.usecase.loginClient.LoginClientResponse
import org.mifos.openbanking.domain.usecase.createTransactionRequest.CreateTransactionRequestRequest
import org.mifos.openbanking.domain.usecase.createTransactionRequest.CreateTransactionRequestResponse

class ClientApi {

    private val client = HttpClient()

    suspend fun createClient(
        name: String,
        contact: String,
        scopes: ArrayList<String>,
        redirectUris: ArrayList<String>
    ): Response<CreateClientResponse> {
        return Response.Error(IllegalStateException())
    }

    suspend fun loginClient(request: LoginClientRequest): Response<LoginClientResponse> {
        try {
            val response = client.post<String>(API_HOST + DIRECT_AUTHENTICATION_PATH) {
                headers {
                    append(
                        "Authorization",
                        "DirectLogin username=${request.username},password=${request.password},consumer_key=${request.consumer_key}"
                    )
                }
            }

            val loginClientResponse = Json.parse(LoginClientResponse.serializer(), response)
            return Response.Success(loginClientResponse)

        } catch (exp: ClientRequestException) {
            return Response.Error(exp)
        } catch (exp: Exception) {
            return Response.Error(exp)
        }
    }

    suspend fun fetchAccounts(request: FetchAccountsRequest): Response<FetchAccountsResponse> {
        try {
            val response = client.get<String>(API_HOST + ACCOUNTS_PATH) {
                headers {
                    append("Authorization", "DirectLogin token=${request.token}")
                }
            }

            val responseJson = Json.parseJson(response)
            val responseAccounts = (responseJson as Map<String, JsonArray>)["accounts"].toString()
            val accounts = Json.nonstrict.parse(Account.serializer().list, responseAccounts)
            return Response.Success(
                FetchAccountsResponse(
                    accounts
                )
            )

        } catch (exp: ClientRequestException) {
            return Response.Error(exp)
        } catch (exp: Exception) {
            return Response.Error(exp)
        }
    }

    suspend fun fetchBalances(request: FetchBalancesRequest): Response<FetchBalancesResponse> {
        try {
            val response = client.get<String>(
                API_HOST + bankBalancePath(
                    request.bankId
                )
            ) {
                headers {
                    append("Authorization", "DirectLogin token=${request.token}")
                }
            }

            val accountBalances: MutableList<AccountBalance> = mutableListOf()

            val accounts = (Json.parseJson(response) as JsonObject)["accounts"] as JsonArray
            for (account in accounts) {
                account as JsonObject
                val accountId = account["id"]!!.content
                val balance = account["balance"] as JsonObject
                val currency = balance["currency"]!!.content
                val amount = balance["amount"]!!.primitive.double

                accountBalances.add(
                    AccountBalance(
                        accountId,
                        currency,
                        amount
                    )
                )
            }

            return Response.Success(
                FetchBalancesResponse(
                    accountBalances
                )
            )

        } catch (exp: ClientRequestException) {
            return Response.Error(exp)
        } catch (exp: Exception) {
            return Response.Error(exp)
        }
    }

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
}