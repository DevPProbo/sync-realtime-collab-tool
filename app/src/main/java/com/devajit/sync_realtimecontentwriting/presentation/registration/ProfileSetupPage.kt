package com.devajit.sync_realtimecontentwriting.presentation.registration

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.devajit.sync_realtimecontentwriting.ui.theme.colorSecondary
import com.devajit.sync_realtimecontentwriting.ui.theme.primaryColor
import com.devajit.sync_realtimecontentwriting.ui.theme.textColorPrimary
import com.devajit.sync_realtimecontentwriting.ui.theme.textColorSecondary


@Composable
fun ProfileSetup(
    userName: String,
    userEmail: String,
    password:String,
    viewModel: SignUpViewModel,
    navController: NavController,
    context: Context
) {
    val fullName = remember { mutableStateOf("") }

    var isLoading by rememberSaveable { mutableStateOf(false) }
    LaunchedEffect(viewModel.event.value) {
        isLoading = false
    }

    var selectImage by remember { mutableStateOf<Uri?>(null) }
    val galleryLauncher= rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ){uri: Uri? ->
        selectImage=uri
    }

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(Modifier.height(8.dp))

        Row(horizontalArrangement = Arrangement.Start,
            modifier = Modifier.fillMaxWidth()) {
            Icon(
                Icons.Filled.ArrowBack,
                tint = textColorPrimary,
                contentDescription = "Back",
                modifier = Modifier
                    .size(24.dp)
                    .clickable {
                        navController.popBackStack()
                    }
            )
            Spacer(Modifier.width(104.dp))
            Text(
                "Set Up Profile", fontSize = 18.sp, color = textColorPrimary,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(Modifier.height(36.dp))

        Box(
            Modifier
                .size(95.dp)
                .clickable {
                    galleryLauncher.launch("image/*")
                }
                .clip(CircleShape),
            contentAlignment = Alignment.Center
        ){
            if (selectImage!=null){
                Image(
                    painter = rememberAsyncImagePainter(selectImage),
                    contentDescription = "user image",
                    Modifier
                        .size(95.dp)
                        .clip(CircleShape)
                )
            }else{
                Box(
                    Modifier.background(
                        colorSecondary, RoundedCornerShape(90.dp)
                    ).size(96.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Filled.CameraAlt,
                        tint = textColorSecondary,
                        contentDescription = "App logo",
                        modifier = Modifier
                            .size(48.dp)
                    )
                }
            }
        }

        Spacer(Modifier.height(36.dp))

        TextField(
            shape = RoundedCornerShape(10.dp),
            value = fullName.value,
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
                Text("Full Name", color = textColorSecondary)
            },
            textStyle = TextStyle(
                fontSize = 15.sp
            ),
            modifier = Modifier
                .fillMaxWidth()
                .background(shape = RoundedCornerShape(90.dp), color = colorSecondary),
            onValueChange = { value ->
                fullName.value = value
            },
            placeholder = {
                Text("Enter full name")
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
                        if (userName.isNotEmpty() &&
                            userEmail.isNotEmpty() &&
                            password.isNotEmpty() &&
                            fullName.value.isNotEmpty() &&
                            selectImage!=null
                        ) {
                            viewModel.signUpUser(userName,fullName.value,userEmail,password,selectImage!!)
                            isLoading = true
                        } else {
                            viewModel.event.value = "Please fill all the details"
                        }
                    },
                ) {
                    Text("Complete profile", fontSize = 16.sp,
                        color = textColorPrimary)

                }
            }


        }
    }
}

