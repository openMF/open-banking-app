package org.mifos.openbanking.domain.usecase.fetchTransactions

import org.mifos.openbanking.domain.usecase.base.BaseRequest


class FetchTransactionsRequest(
    val token: String,
    val bankId: String,
    val accountId: String,
    val sort: Sort = Sort.DESCENDING,
    val limit: Long = 50,
    val offset: Long = 0,
    val fromDate: String = "0000-00-00T00:00:00.000Z",
    val toDate: String = "3049-01-01T00:00:00.000Z"

) :
    BaseRequest {

    override fun validate(): Boolean {
        return true
    }

}

enum class Sort(val direction: String) {
    ASCENDING("ASC"),
    DESCENDING("DESC")
}