package org.mifos.openbanking.common.viewModel.account

sealed class AccountState

object SuccessAuthState : AccountState()
object LoadingAuthState : AccountState()
class ErrorAuthState(val message: String?) : AccountState()