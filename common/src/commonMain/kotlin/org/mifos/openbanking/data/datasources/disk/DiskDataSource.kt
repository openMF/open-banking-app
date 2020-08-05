package org.mifos.openbanking.data.datasources.disk

import org.mifos.openbanking.LocalDatabase
import org.mifos.openbanking.data.datasources.disk.dao.AccountDao

class DiskDataSource {

    private val database: LocalDatabase = DatabaseCreator.getDatabase()

    fun isUserLoggedIn(): Boolean {
        return false
    }

}