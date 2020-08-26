package org.mifos.openbanking.domain.usecase.fetchCards

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.mifos.openbanking.domain.usecase.fetchAccounts.Account

@Serializable
class FetchCardsResponse(
    @SerialName("cards") val cardsList: List<Card>
)

@Serializable
data class Card(
    @SerialName("bank_id")
    val bankId: String,
    @SerialName("bank_card_number")
    val cardNumber: Long,
    @SerialName("name_on_card")
    val name: String,
    @SerialName("valid_from_date")
    val validFrom: String,
    @SerialName("expires_date")
    val validTill: String,
    val enabled: Boolean,
    val cancelled: Boolean,
    val account: Account
)