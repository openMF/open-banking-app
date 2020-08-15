package org.mifos.openbanking.viewModel.transaction

import org.mifos.openbanking.viewModel.model.TransactionRequestModel

sealed class FetchTransactionState

class SuccessFetchTransactionState(val transactionRequestModelList: List<TransactionRequestModel>) :
    FetchTransactionState()

object LoadingFetchTransactionState : FetchTransactionState()
class ErrorFetchTransactionState(val message: String?) : FetchTransactionState()