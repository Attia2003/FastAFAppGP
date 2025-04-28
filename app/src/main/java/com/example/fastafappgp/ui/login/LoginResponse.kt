package com.example.fastafappgp.ui.login

data class LoginResponse(
    val accessTokenExpiration: Long,
    val refreshTokenExpiration: Long,
    val accessToken: String,
    val refreshToken: String
)
