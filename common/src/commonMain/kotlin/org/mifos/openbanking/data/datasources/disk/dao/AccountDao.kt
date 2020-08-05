package org.mifos.openbanking.data.datasources.disk.dao

import org.mifos.openbanking.AccountModel
import org.mifos.openbanking.LocalDatabase


class AccountDao(database: LocalDatabase) {

    private val db = database.accountModelQueries

    internal fun insert(accountId: String, bankId: String) {
        db.insertItem(
            accoundId = accountId,
            bankId = bankId
        )
    }

    internal fun select(): List<AccountModel> = db.selectAll().executeAsList()
}