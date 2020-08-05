package org.mifos.openbanking.data.datasources.disk.preferencesHelper

import com.github.florent37.preferences.Preferences
import kotlinx.serialization.json.Json
import org.mifos.openbanking.viewModel.model.UserModel

class PreferencesHelper {
    private val preferences: Preferences = Preferences()

    private val userModelKey: String = "user_model"

    fun saveUserModel(userModel: UserModel) {
        preferences.setString(userModelKey, Json.stringify(UserModel.serializer(), userModel))
    }

    fun getUserModel(): UserModel? {
        return if (preferences.hasKey(userModelKey)) {
            Json.parse(UserModel.serializer(), preferences.getString(userModelKey)!!)
        } else {
            null
        }
    }
}