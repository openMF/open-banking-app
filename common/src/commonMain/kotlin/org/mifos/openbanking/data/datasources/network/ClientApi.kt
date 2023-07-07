package org.mifos.openbanking.data.datasources.network

import io.ktor.client.HttpClient
import io.ktor.client.features.ClientRequestException
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.post
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.double
import kotlinx.serialization.json.jsonPrimitive
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

            val loginClientResponse = Json.decodeFromString(LoginClientResponse.serializer(), response)
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

            val responseJson = Json.parseToJsonElement(response)
            val responseAccounts = (responseJson as Map<String, JsonArray>)["accounts"].toString()
            val accounts = Json.decodeFromString(ListSerializer(Account.serializer()), responseAccounts)
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

            val accounts = (Json.parseToJsonElement(response) as JsonObject)["accounts"] as JsonArray
            for (account in accounts) {
                account as JsonObject
                val accountId = account["id"]!!.jsonPrimitive.content
                val balance = account["balance"] as JsonObject
                val currency = balance["currency"]!!.jsonPrimitive.content
                val amount = balance["amount"]!!.jsonPrimitive.double

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
}