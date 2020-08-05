package org.mifos.openbanking.viewModel.account

import org.mifos.openbanking.viewModel.model.AccountModel

sealed class AccountState

class SuccessAccountState(
    val accountList: List<AccountModel>,
    val balancesFetched: Boolean = false
) : AccountState()

object LoadingAccountState : AccountState()
class ErrorAccountState(val message: String?) : AccountState()