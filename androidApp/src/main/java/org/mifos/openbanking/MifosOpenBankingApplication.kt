package org.mifos.openbanking

import android.app.Application
import org.mifos.openbanking.viewModel.app.App

class MifosOpenBankingApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        println("Application init")

        context = this
        App.appLaunch()
    }
}