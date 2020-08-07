package org.mifos.openbanking.domain.usecase.transferMoney

import org.mifos.openbanking.domain.usecase.base.BaseRequest

class TransferMoneyRequest(
    val token: String,
    val sourceBankId: String,
    val sourceAccountId: String,
    val destinationBankId: String,
    val destinationAccountId: String,
    val currency: String,
    val amount: Double,
    val description: String
) :
    BaseRequest {

    override fun validate(): Boolean {
        return true
    }

}