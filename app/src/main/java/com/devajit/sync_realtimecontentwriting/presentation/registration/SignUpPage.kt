package com.devajit.sync_realtimecontentwriting.presentation.registration

import android.content.Context
import android.util.Patterns
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.devajit.sync_realtimecontentwriting.R
import com.devajit.sync_realtimecontentwriting.presentation.Screens
import com.devajit.sync_realtimecontentwriting.ui.theme.colorSecondary
import com.devajit.sync_realtimecontentwriting.ui.theme.primaryColor
import com.devajit.sync_realtimecontentwriting.ui.theme.textColorPrimary
import com.devajit.sync_realtimecontentwriting.ui.theme.textColorSecondary
import java.util.regex.Pattern


@Composable
fun SignUpPage(
    viewModel: SignUpViewModel, navController: NavController,
    context: Context
) {

    val userName = remember { mutableStateOf("") }
    val userEmail = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    var isLoading by rememberSaveable { mutableStateOf(false) }
    LaunchedEffect(viewModel.event.value) {
        isLoading = false
    }
    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(Modifier.height(18.dp))

        Image(
            painterResource(id = R.drawable.logo), contentDescription = "App logo",
            Modifier.size(96.dp)
        )

        Spacer(Modifier.height(12.dp))

        Text(
            "Sign up to continue",
            textAlign = TextAlign.Center,
            fontSize = 22.sp,
            fontWeight = FontWeight.SemiBold,
            color = textColorPrimary
        )
        Spacer(Modifier.height(36.dp))
        TextField(
            shape = RoundedCornerShape(10.dp),
            value = userName.value,
            colors = TextFieldDefaults.colors(
                unfocusedTextColor = textColorPrimary,
                focusedTextColor = textColorPrimary,
                focusedPlaceholderColor = textColorSecondary,
                unfocusedPlaceholderColor = textColorSecondary,
                focusedContainerColor = colorSecondary,
                unfocusedContainerColor = colorSecondary,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
            ),
            textStyle = TextStyle(
                fontSize = 15.sp
            ),
            modifier = Modifier
                .fillMaxWidth()
                .background(shape = RoundedCornerShape(90.dp), color = colorSecondary),
            onValueChange = { value ->
                userName.value = value.lowercase()
                    .trim()
            },
            label = {
                Text("Username", color = textColorSecondary)
            },
            placeholder = {
                Text("Enter username")
            })


        Spacer(Modifier.height(12.dp))

        TextField(
            shape = RoundedCornerShape(10.dp),
            value = userEmail.value,
            colors = TextFieldDefaults.colors(
                unfocusedTextColor = textColorPrimary,
                focusedTextColor = textColorPrimary,
                focusedPlaceholderColor = textColorSecondary,
                unfocusedPlaceholderColor = textColorSecondary,
                focusedContainerColor = colorSecondary,
                unfocusedContainerColor = colorSecondary,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
            ),
            label = {
                Text("Email", color = textColorSecondary)
            },
            textStyle = TextStyle(
                fontSize = 15.sp
            ),
            modifier = Modifier
                .fillMaxWidth()
                .background(shape = RoundedCornerShape(90.dp), color = colorSecondary),
            onValueChange = { value ->
                userEmail.value = value.replaceFirstChar {
                    it.lowercase()
                }
            },
            placeholder = {
                Text("Enter Email")
            })

        Spacer(Modifier.height(12.dp))

        TextField(
            shape = RoundedCornerShape(10.dp),
            value = password.value,
            singleLine = true,
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            colors = TextFieldDefaults.colors(
                unfocusedTextColor = textColorPrimary,
                focusedTextColor = textColorPrimary,
                focusedPlaceholderColor = textColorSecondary,
                unfocusedPlaceholderColor = textColorSecondary,
                focusedContainerColor = colorSecondary,
                unfocusedContainerColor = colorSecondary,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
            ),
            label = {
                Text("Password", color = textColorSecondary)
            },
            textStyle = TextStyle(
                fontSize = 15.sp
            ),
            modifier = Modifier
                .fillMaxWidth()
                .background(shape = RoundedCornerShape(90.dp), color = colorSecondary),
            onValueChange = { value ->
                password.value = value
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                val image = if (passwordVisible)
                    Icons.Filled.Visibility
                else Icons.Filled.VisibilityOff

                val description = if (passwordVisible) "Hide password" else "Show password"

                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = image, description)
                }
            },
            placeholder = {
                Text("Enter password")
            })

        Spacer(Modifier.height(32.dp))
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            if (isLoading) {
                CircularProgressIndicator(
                    color = primaryColor,
                    modifier = Modifier.size(42.dp)
                )
            } else {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = primaryColor,
                        disabledContainerColor = primaryColor
                    ),
                    onClick = {
                        if (userName.value.isNotEmpty()  &&
                            userEmail.value.isNotEmpty() &&
                            password.value.isNotEmpty()
                        ) {
                            val pattern: Pattern = Patterns.EMAIL_ADDRESS
                            if(pattern.matcher(userEmail.value).matches()) {
                                navController.navigate(
                                    "${Screens.ProfileSetupPage.route}/" +
                                            "${userEmail.value}/${userName.value}/${password.value}"
                                )
                                isLoading = true
                            } else {
                                viewModel.event.value = "Invalid email format!"
                            }
                        } else {
                            viewModel.event.value = "Please fill all the details"
                        }
                    },
                ) {
                    Text("Create account", fontSize = 16.sp, color = textColorPrimary)

                }
            }

            Spacer(Modifier.height(38.dp))

            Text(
                "Already have an account?",
                textAlign = TextAlign.Center,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Gray
            )

            Spacer(Modifier.height(8.dp))

            Text("Log in",
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = textColorPrimary,
                modifier = Modifier.clickable {
                    navController.navigate(Screens.LoginPage.route)
                })
        }
    }
}