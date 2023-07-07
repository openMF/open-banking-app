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

class Preferences(private val name: String? = null) {

    private val preferences = mutableMapOf<String, Any>()

    fun setInt(key: String, value: Int) {
        preferences[key] = value
    }

    fun getInt(key: String, defaultValue: Int): Int {
        return preferences[key] as? Int? ?: defaultValue
    }

    fun getInt(key: String): Int? {
        return preferences[key] as? Int
    }

    fun setFloat(key: String, value: Float) {
        preferences[key] = value
    }

    fun getFloat(key: String, defaultValue: Float): Float {
        return preferences[key] as? Float? ?: defaultValue
    }

    fun getFloat(key: String): Float? {
        return preferences[key] as? Float
    }

    fun setLong(key: String, value: Long) {
        preferences[key] = value
    }

    fun getLong(key: String, defaultValue: Long): Long {
        return preferences[key] as? Long? ?: defaultValue
    }

    fun getLong(key: String): Long? {
        return preferences[key] as? Long
    }

    fun setString(key: String, value: String) {
        preferences[key] = value
    }

    fun getString(key: String, defaultValue: String): String {
        return preferences[key] as? String? ?: defaultValue
    }

    fun getString(key: String): String? {
        return preferences[key] as? String
    }

    fun setBoolean(key: String, value: Boolean) {
        preferences[key] = value
    }

    fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return preferences[key] as? Boolean? ?: defaultValue
    }

    fun getBoolean(key: String): Boolean? {
        return preferences[key] as? Boolean
    }

    fun remove(key: String) {
        preferences.remove(key)
    }

    fun clear() {
        preferences.clear()
    }

    fun hasKey(key: String): Boolean {
        return preferences.containsKey(key)
    }
}


