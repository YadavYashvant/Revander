package com.yashvant.revander.api_calling_feature

import com.yashvant.revander.api_calling_feature.models.UserModel
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

public interface ApiInterface {
    @Headers(
        "Accept: application/json"
    )
    @GET("users/{id}")
    abstract fun getUserById(@Path("id") id: String): Response<UserModel?>?
}