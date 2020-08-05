package org.mifos.openbanking.data.datasources.disk

import com.squareup.sqldelight.db.SqlDriver
import org.mifos.openbanking.LocalDatabase

expect fun getSqlDriver(): SqlDriver

object DatabaseCreator {
    fun getDatabaseName(): String {
        return "openbanking.db"
    }

    fun getDatabase(): LocalDatabase {
        val sqlDriver =
            getSqlDriver()
        return LocalDatabase(sqlDriver)
    }
}