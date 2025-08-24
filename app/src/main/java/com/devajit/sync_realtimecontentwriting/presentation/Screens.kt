package com.devajit.sync_realtimecontentwriting.presentation

sealed class Screens(val route: String) {

    object RegistrationPage : Screens("RegistationPage")
    object NoInternet : Screens("NoInternetPage")
    object LoginPage : Screens("LoginPage")
    object TabScreen : Screens("HomePage")

    object ProfileSetupPage : Screens("ProfileSetupPage")

}