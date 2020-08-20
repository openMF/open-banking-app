package org.mifos.openbanking.domain.usecase.fetchTransactionById

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
class FetchTransactionByIdResponse(
    val transaction: Transaction
)

@Serializable
data class Transaction(
    val id: String,
    @SerialName("this_account")
    val thisAccount: Account,
    val otherAccount: Account,
    val details: Details
) {

    @Serializable
    data class Account(
        val id: String,
        val number: Long,
        val holders: List<Holder>,
        val kind: String?,
        @SerialName("IBAN")
        val iban: String,
        @SerialName("swift_bic")
        val swiftBic: String,
        val bank: Bank
    ) {

        @Serializable
        data class Holder(
            val name: String,
            @SerialName("is_alias")
            val isAlias: Boolean
        )

        @Serializable
        data class Bank(
            @SerialName("national_identifier")
            val nationalIdentifier: String,
            val name: String
        )

    }

    @Serializable
    data class Details(
        val type: String,
        val description: String,
        val posted: String,
        val completed: String,
        @SerialName("new_balance")
        val newBalance: Balance,
        val value: Balance
    ) {
        @Serializable
        data class Balance(val currency: String, val amount: Double)
    }

}