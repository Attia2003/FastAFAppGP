package com.example.fastafappgp.ui.login

sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    object Success : LoginState()
    object Error : LoginState()
}