package org.mifos.openbanking.viewModel.model

data class TransactionRequestModel(
    val id: String,
    val type: String,
    val fromBankId: String,
    val fromAccountId: String,
    val toBankId: String,
    val toAccountId: String,
    val currency: String,
    val amount: Double,
    val description: String
)