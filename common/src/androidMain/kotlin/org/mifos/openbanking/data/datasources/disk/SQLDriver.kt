package org.mifos.openbanking.data.datasources.disk

import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import org.mifos.openbanking.LocalDatabase
import org.mifos.openbanking.context

actual fun getSqlDriver(): SqlDriver {
    return AndroidSqliteDriver(LocalDatabase.Schema, context, DatabaseCreator.getDatabaseName())
}