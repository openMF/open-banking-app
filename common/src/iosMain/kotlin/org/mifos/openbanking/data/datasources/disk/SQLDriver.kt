package org.mifos.openbanking.data.datasources.disk

import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver
import org.mifos.openbanking.LocalDatabase

actual fun getSqlDriver(): SqlDriver {
    return NativeSqliteDriver(LocalDatabase.Schema, DatabaseCreator.getDatabaseName())
}