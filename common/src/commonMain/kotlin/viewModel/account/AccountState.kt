package org.mifos.openbanking.common.viewModel.account

import org.mifos.openbanking.common.viewModel.model.AccountModel

sealed class AccountState

class SuccessAccountState(
    val accountList: List<AccountModel>,
    val balancesFetched: Boolean = false
) : AccountState()

object LoadingAccountState : AccountState()
class ErrorAccountState(val message: String?) : AccountState()