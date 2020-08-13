package org.mifos.openbanking.viewModel.transaction


sealed class CreateTransactionRequestState

object SuccessCreateTransactionRequestState : CreateTransactionRequestState()
object LoadingCreateTransactionRequestState : CreateTransactionRequestState()
class ErrorCreateTransactionRequestState(val message: String?) : CreateTransactionRequestState()