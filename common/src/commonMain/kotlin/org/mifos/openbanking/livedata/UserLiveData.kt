package org.mifos.openbanking.livedata

import dev.icerock.moko.mvvm.livedata.MutableLiveData
import org.mifos.openbanking.viewModel.model.UserModel

object UserLiveData {

    private val userModelLiveData: MutableLiveData<UserModel> = MutableLiveData(UserModel())

    fun updateUserModel(userModel: UserModel) {
        userModelLiveData.postValue(userModel)
    }

    fun getUserModel(): UserModel {
        return userModelLiveData.value
    }

}