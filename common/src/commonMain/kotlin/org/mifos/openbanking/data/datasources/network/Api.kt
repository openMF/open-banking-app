package org.mifos.openbanking.data.datasources.network

const val CONSUMER_KEY = "lxhlkssvfgoheqjdhdr4tq1lkj5h5jklhn2rqxd0"

const val API_HOST = "https://apisandbox.openbankproject.com/"

const val DIRECT_AUTHENTICATION_PATH = "my/logins/direct"
const val ACCOUNTS_PATH = "obp/v4.0.0/my/accounts"
const val BANKS_PATH = "obp/v4.0.0/banks"
const val CARDS_PATH = "obp/v4.0.0/cards"


fun bankBalancePath(bankId: String): String {
    return "obp/v4.0.0/banks/$bankId/balances"
}

fun createTransactionRequestPath(bankId: String, accountId: String): String {
    return "obp/v4.0.0/banks/$bankId/accounts/$accountId/owner/transaction-request-types/SANDBOX_TAN/transaction-requests"
}

fun fetchTransactionRequestsPath(bankId: String, accountId: String): String {
    return "/obp/v4.0.0/banks/$bankId/accounts/$accountId/owner/transaction-requests"
}