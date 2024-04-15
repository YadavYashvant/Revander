package com.yashvant.revander.api_calling_feature

data class ProfileModel(
    var age: String,
    var name: String,
    var email: String,
)

data class UserModel(
    var profile: ProfileModel
)

