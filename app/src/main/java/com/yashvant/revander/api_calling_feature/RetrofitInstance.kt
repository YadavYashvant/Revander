package com.yashvant.revander.api_calling_feature

import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import com.yashvant.revander.api_calling_feature.models.ProfileModel
import com.yashvant.revander.api_calling_feature.models.UserModel
import com.yashvant.revander.utils.Util
import com.yashvant.revander.utils.Util.baseurl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


fun sendRequest(
    id: String,
//    context: Context,
    profileState: MutableState<ProfileModel>
) {
    val retrofit = Retrofit.Builder()
        .baseUrl(baseurl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api = retrofit.create(ApiInterface::class.java)

    val call: Call<UserModel?>? = api.getUserById(id);

    call!!.enqueue(object: Callback<UserModel?> {
        override fun onResponse(call: Call<UserModel?>, response: Response<UserModel?>) {
            if(response.isSuccessful) {
                Log.d("Main", "success!" + response.body().toString())
                profileState.value = response.body()!!.profile
            }
        }

        override fun onFailure(call: Call<UserModel?>, t: Throwable) {
            Log.e("Main", "API calling Failed!!" + t.message.toString())
        }
    })
}
