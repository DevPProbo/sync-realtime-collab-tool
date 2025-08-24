package com.devajit.sync_realtimecontentwriting.presentation.appActivities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.devajit.sync_realtimecontentwriting.core.utils.Constants
import com.devajit.sync_realtimecontentwriting.presentation.Screens
import com.devajit.sync_realtimecontentwriting.presentation.UIEvents
import com.devajit.sync_realtimecontentwriting.presentation.appActivities.ActivityHelper.showDeepLinkDocument
import com.devajit.sync_realtimecontentwriting.presentation.document.DocumentViewModel
import com.devajit.sync_realtimecontentwriting.presentation.registration.LoginPage
import com.devajit.sync_realtimecontentwriting.presentation.registration.ProfileSetup
import com.devajit.sync_realtimecontentwriting.presentation.registration.SignUpPage
import com.devajit.sync_realtimecontentwriting.presentation.registration.SignUpViewModel
import com.devajit.sync_realtimecontentwriting.presentation.tabScreens.TabScreenPage
import com.devajit.sync_realtimecontentwriting.presentation.tabScreens.NoInternetPage
import com.devajit.sync_realtimecontentwriting.ui.theme.backgroundColor
import com.devajit.sync_realtimecontentwriting.ui.theme.primaryColor
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var documentViewModel: DocumentViewModel
    private lateinit var userViewModel: SignUpViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userViewModel = viewModels<SignUpViewModel>().value
        val loggedData by userViewModel.userState
        val networkAvailable by userViewModel.isInternetAvailable

        fun handleIntent(intent: Intent?) {
            if (!intent?.dataString.isNullOrEmpty()) {
                if (!userViewModel.userState.value.id.isNullOrEmpty()) {
                    val appLinkAction: String? = intent?.action
                    val appLinkData: Uri? = intent?.data
                    Log.d("validDeepLinkUrl", appLinkData.toString())
                    val docCode = showDeepLinkDocument(appLinkAction, appLinkData)
                    if (docCode.isNotEmpty()) {
                        val intent_ =
                            Intent(this@MainActivity, DocumentActivity::class.java)
                        intent_.putExtra("documentId", docCode)
                        intent_.putExtra("applyDocCheck", true)
                        startActivity(intent_)

                    }
                } else {
                    Toast.makeText(
                        this, "You must be logged in to access the document.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        setContent {
            val context = LocalContext.current
            val scaffoldState = remember { SnackbarHostState() }
            val currentPage = rememberSaveable {
                mutableStateOf("")
            }
            val navController = rememberNavController()

            LaunchedEffect(userViewModel.event.value) {
                if (userViewModel.event.value.isNotEmpty()) {
                    Toast.makeText(context, userViewModel.event.value, Toast.LENGTH_SHORT).show()
                    userViewModel.event.value = ""
                }
            }
            LaunchedEffect(networkAvailable, loggedData) {
                if (userViewModel.checkNetworkAvailable()) {
                    userViewModel.initData()
                    delay(500)
                    Log.d("userData__", loggedData.id.isNullOrEmpty().toString())
                    if (!loggedData.id.isNullOrEmpty()) {
                        documentViewModel.setUser()
                        currentPage.value = Screens.TabScreen.route

                    } else {
                        currentPage.value = Screens.RegistrationPage.route
                    }

                    handleIntent(intent)
                } else {
                    currentPage.value = Screens.NoInternet.route
                }
            }

            LaunchedEffect(documentViewModel.eventFlow) {

                documentViewModel.eventFlow.collectLatest { event ->
                    when (event) {
                        is UIEvents.ShareDocument -> {
                           val collectedEvent : UIEvents.ShareDocument = event
                            collectedEvent.documentId.let { documentId ->
                                val sendIntent = Intent()
                                sendIntent.setAction(Intent.ACTION_SEND)
                                sendIntent.putExtra(
                                    Intent.EXTRA_TEXT,
                                    "Hey there! I invite you to collaborate with me in my content creation on Sync App: ${
                                        Constants.appDeepLinkUrl.plus(
                                            documentId
                                        )
                                    }"
                                )
                                sendIntent.setType("text/plain")
                                startActivity(Intent.createChooser(sendIntent, "Share Document"))
                                documentViewModel.emitUIEvent(UIEvents.DefaultState())
                            }
                        }
                        is UIEvents.DocumentCodeApplied -> {
                            val collectedEvent : UIEvents.DocumentCodeApplied = event
                            collectedEvent.documentId.let { documentId ->
                                if(documentId.isNotEmpty()) {
                                    val intent = Intent(context, DocumentActivity::class.java)
                                    intent.putExtra("documentId", documentId)
                                    context.startActivity(intent)
                                }
                            }

                        }

                        else -> {}
                    }
                }

            }
            Scaffold(
                snackbarHost = { SnackbarHost(hostState = scaffoldState) }) {
                it
                Box(
                    Modifier
                        .fillMaxSize()
                        .background(backgroundColor)

                ) {
                    if (currentPage.value.isNotEmpty()) {
                        NavHost(
                            navController = navController,
                            startDestination = currentPage.value,
                        ) {

                            composable(
                                route = Screens.RegistrationPage.route
                            ) {
                                SignUpPage(userViewModel, navController, context)
                            }
                            composable(
                                route = Screens.LoginPage.route
                            ) {
                                LoginPage(userViewModel, navController)
                            }

                            composable(
                                route = Screens.TabScreen.route
                            ) {
                                TabScreenPage(userViewModel, documentViewModel)
                            }

                            composable(
                                route = Screens.NoInternet.route
                            ) {
                                NoInternetPage {
                                    userViewModel.checkNetworkAvailable()
                                }

                            }
                            composable(
                                route = "${Screens.ProfileSetupPage.route}/{userEmail}/{userName}/{password}",
                                arguments = listOf(
                                    navArgument("userName") { type = NavType.StringType },
                                    navArgument("userEmail") { type = NavType.StringType },
                                    navArgument("password") { type = NavType.StringType }

                                )
                            ){backStackEntry->
                                val userName = backStackEntry.arguments?.getString("userName") ?: ""
                                val userEmail = backStackEntry.arguments?.getString("userEmail") ?: ""
                                val password = backStackEntry.arguments?.getString("password") ?: ""

                                ProfileSetup(
                                    userName = userName,
                                    userEmail = userEmail,
                                    password= password,
                                    viewModel=userViewModel,
                                    navController =navController ,
                                    context = context
                                )
                            }
                        }
                    } else {
                        Column(
                            Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            CircularProgressIndicator(
                                color = primaryColor,
                                modifier = Modifier.size(42.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
