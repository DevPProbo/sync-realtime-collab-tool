package com.devajit.sync_realtimecontentwriting.domain.use_case

import android.net.Uri
import com.devajit.sync_realtimecontentwriting.domain.model.UserModel
import com.devajit.sync_realtimecontentwriting.domain.repository.IUserRepository
import kotlinx.coroutines.flow.Flow

class UserUseCase(
    private val userRepository: IUserRepository
) {

    fun createUser(userName : String, fullName : String,userEmail:String, password: String,userPicture: Uri) : Flow<UserModel> {
        return userRepository.signupUser(userName, fullName,userEmail, password,userPicture)
    }

    fun loginUser(userName : String, password: String) : Flow<UserModel> {
        return userRepository.loginUser(userName, password)
    }

    fun getUserDetails() : Flow<UserModel> {
        return userRepository.getUserDetails()
    }

    fun logOutUser() : Flow<Boolean> {
        return userRepository.logOutUser()
    }
}