package org.mifos.openbanking.data.datasources.disk.preferencesHelper

import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.mifos.openbanking.domain.usecase.fetchBanks.Bank
import org.mifos.openbanking.viewModel.model.UserModel

class PreferencesHelper {
    private val preferences: Preferences = Preferences()

    private val userModelKey: String = "user_model"
    private val supportedBanksKey: String = "supported_banks"

    fun saveUserModel(userModel: UserModel) {
        preferences.setString(userModelKey, Json.encodeToString(userModel))
    }

    fun getUserModel(): UserModel? {
        return if (preferences.hasKey(userModelKey)) {
            Json.decodeFromString(UserModel.serializer(),preferences.getString(userModelKey)!!)
        } else {
            null
        }
    }


    fun saveSupportedBanks(bankList: List<Bank>) {
        preferences.setString(supportedBanksKey, Json.encodeToString(bankList))
    }


    fun getSupportedBanks(): List<Bank> {
        return Json.decodeFromString(ListSerializer(Bank.serializer()), preferences.getString(supportedBanksKey)!!)
    }
}

class Preferences(name: String? = null) {

    fun setInt(key: String, value: Int) {
        // Implementation
    }

    fun getInt(key: String, defaultValue: Int): Int {
        return TODO("Provide the return value")
    }

    fun getInt(key: String): Int? {
        return TODO("Provide the return value")
    }

    fun setFloat(key: String, value: Float) {
        // Implementation
    }

    fun getFloat(key: String, defaultValue: Float): Float {
        return TODO("Provide the return value")
    }

    fun getFloat(key: String): Float? {
        return TODO("Provide the return value")
    }

    fun setLong(key: String, value: Long) {
        // Implementation
    }

    fun getLong(key: String, defaultValue: Long): Long {
        return TODO("Provide the return value")
    }

    fun getLong(key: String): Long? {
        return TODO("Provide the return value")
    }

    fun setString(key: String, value: String) {
        // Implementation
    }

    fun getString(key: String, defaultValue: String): String {
        return TODO("Provide the return value")
    }

    fun getString(key: String): String? {
        return TODO("Provide the return value")
    }

    fun setBoolean(key: String, value: Boolean) {
        // Implementation
    }

    fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return TODO("Provide the return value")
    }

    fun getBoolean(key: String): Boolean? {
        return TODO("Provide the return value")
    }

    fun remove(key: String) {
        // Implementation
    }

    fun clear() {
        // Implementation
    }

    fun hasKey(key: String): Boolean {
        return TODO("Provide the return value")
    }
}

