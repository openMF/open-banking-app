package org.mifos.openbanking

import android.app.Application

class MifosOpenBankingApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        println("Application init")

        context = this
    }
}