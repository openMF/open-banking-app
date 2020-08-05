package org.mifos.openbanking.domain.usecase.fetchBanks

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

class FetchBanksResponse(val bankList: List<Bank>)

@Serializable
class Bank(
    val id: String,
    @SerialName("short_name")
    val shortName: String?,
    @SerialName("full_name")
    val fullName: String?,
    @SerialName("logo")
    val logoUrl: String?,
    @SerialName("website")
    val websiteUrl: String?
)