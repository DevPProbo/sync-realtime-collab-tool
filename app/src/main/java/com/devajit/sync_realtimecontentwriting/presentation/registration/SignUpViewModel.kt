package com.devajit.sync_realtimecontentwriting.presentation.registration

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devajit.sync_realtimecontentwriting.core.utils.AppNetworkManager
import com.devajit.sync_realtimecontentwriting.domain.model.UserModel
import com.devajit.sync_realtimecontentwriting.domain.use_case.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val userUseCase: UserUseCase,
    private val appNetworkManager: AppNetworkManager
) : ViewModel() {

    private var _userState = mutableStateOf(UserModel())
    val userState = _userState
    val event = mutableStateOf("")

    val isInternetAvailable = mutableStateOf(true)

    suspend fun initData() : Boolean{
        var logged = false
        runBlocking {
            userUseCase.getUserDetails().collectLatest { user ->
                Log.d("userData", user.toString())
                if(user.id != null) {
                    if(user.id.isNotBlank()) {
                        _userState.value = user
                        logged = true
                    }
                }
            }
        }
        return logged
    }

    fun checkNetworkAvailable() : Boolean {
         isInternetAvailable.value = appNetworkManager.isNetworkAvailable()
        return isInternetAvailable.value
    }
    fun signUpUser(userName: String, fullName : String,userEmail:String,password : String,userPicture:Uri) {
        viewModelScope.launch {
            userUseCase.createUser(userName, fullName,userEmail, password,userPicture).collectLatest { user ->
                if(!user.id.isNullOrEmpty()) {
                    _userState.value = user
                    event.value = ""
                }
                else {
                    event.value = "This username already exists! Please use a different username!"
                }
            }
        }
    }

    fun loginUser(userName: String,password : String) {
        viewModelScope.launch {
            userUseCase.loginUser(userName, password).collectLatest { user ->
                if(!user.id.isNullOrEmpty()) {
                    _userState.value = user
                    event.value = ""
                }
                else {
                    event.value = "Incorrect username or password! Please try again"
                }
            }
        }
    }
    fun logOutUser() {
        viewModelScope.launch {
            userUseCase.logOutUser()
            userState.value = UserModel()
            event.value = "Logged out!"
            Log.d("UserLoggedOut", "true")
        }
    }

}