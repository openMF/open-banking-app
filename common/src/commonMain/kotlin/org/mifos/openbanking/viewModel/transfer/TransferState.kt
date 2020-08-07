package org.mifos.openbanking.viewModel.transfer


sealed class TransferState

object SuccessTransferState : TransferState()
object LoadingTransferState : TransferState()
class ErrorTransferState(val message: String?) : TransferState()