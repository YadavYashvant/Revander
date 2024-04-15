package com.yashvant.revander.api_calling_feature

import android.util.Log
import androidx.compose.runtime.MutableState
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun sendRequest(
    id: String,
    profileState: MutableState<ProfileModel>
) {
    val retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.9.208:3000")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api = retrofit.create(UserApi::class.java)

    val call: Call<UserModel?>? = api.getUserById(id);

    call!!.enqueue(object: Callback<UserModel?> {
        override fun onResponse(call: Call<UserModel?>, response: Response<UserModel?>) {
            if(response.isSuccessful) {
                Log.d("Main", "success!" + response.body().toString())
                profileState.value = response.body()!!.profile
            }
        }

        override fun onFailure(call: Call<UserModel?>, t: Throwable) {
            Log.e("Main", "Failed mate " + t.message.toString())
        }
    })
}