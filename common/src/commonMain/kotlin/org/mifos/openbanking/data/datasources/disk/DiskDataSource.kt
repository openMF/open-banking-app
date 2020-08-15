package org.mifos.openbanking.data.datasources.disk

import org.mifos.openbanking.LocalDatabase
import org.mifos.openbanking.data.datasources.disk.preferencesHelper.PreferencesHelper
import org.mifos.openbanking.domain.usecase.fetchBanks.Bank
import org.mifos.openbanking.viewModel.model.UserModel

class DiskDataSource {

    private val database: LocalDatabase = DatabaseCreator.getDatabase()
    private val preferencesHelper: PreferencesHelper = PreferencesHelper()

    init {

    }

    fun isUserLoggedIn(): Boolean {
        val userModel = preferencesHelper.getUserModel()
        return userModel != null
    }

    fun getUserModel(): UserModel {
        return preferencesHelper.getUserModel()!!
    }

    fun saveUserModel(userModel: UserModel) {
        preferencesHelper.saveUserModel(userModel)
    }

    fun saveSupportedBanks(bankList: List<Bank>) {
        preferencesHelper.saveSupportedBanks(bankList)
    }

    fun getSupportedBanks(): List<Bank> {
        return preferencesHelper.getSupportedBanks()
    }

}