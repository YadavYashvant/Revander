package com.yashvant.revander.api_calling_feature

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
//                backgroundColor = Purple700,
                title = {
                    Text(
                        text = "Simple API Request",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        color = Color.White
                    )
                }
            )
        },
        content = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val id = remember {
                    mutableStateOf(TextFieldValue())
                }

                val profile = remember {
                    mutableStateOf(ProfileModel(
                        age = "",
                        name = "",
                        email = ""
                    ))
                }

                Text(
                    text="API Sample",
                    style= TextStyle(
                        fontSize = 40.sp,
                        fontFamily = FontFamily.Cursive
                    )
                )

                Spacer(modifier = Modifier.height(15.dp))

                TextField(
                    label = { Text(text = "User ID")},
                    value = id.value,
                    onValueChange = { id.value = it }
                )

                Spacer(modifier = Modifier.height(15.dp))

                Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
                    Button(
                        onClick = {
                            val data = sendRequest(
                                id = id.value.text,
                                profileState = profile
                            )

                            Log.d("Main Activity", profile.toString())
                        }
                    ) {
                        Text(text = "Get Data")
                    }
                }

                Spacer(modifier = Modifier.height(15.dp))

                Text(text = profile.component1().toString(), fontSize = 40.sp)
            }
        }
    )
}
