package com.example.fastafappgp.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fastafappgp.apimanager.ApiManager
import com.example.fastafappgp.util.SingleLiveEvent
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    val username = MutableLiveData<String>("attia")
    val password = MutableLiveData<String>("attiapass")

    val loginResponse = MutableLiveData<LoginResponse>()
    val loginError = MutableLiveData<String>()

    val event = SingleLiveEvent<NavigateEvent>()

    fun login() {
        val user = username.value ?: ""
        val pass = password.value ?: ""

        if (user.isBlank() || pass.isBlank()) {
            loginError.postValue("Username or password cannot be empty")
            return
        }

        viewModelScope.launch {
            try {

                val apiManager = ApiManager()
                val response = apiManager.getWebService().login(LoginRequest(user, pass))

                if (response.isSuccessful) {
                    response.body()?.let { loginResp ->
                        TokenManager.saveAccessToken(loginResp.accessToken)
                        TokenManager.saveRefreshToken(loginResp.refreshToken)

                        loginResponse.postValue(loginResp)
                        event.postValue(NavigateEvent.NavigateToCart)

                    } ?: run {
                        loginError.postValue("Empty response body")
                    }
                } else {
                    loginError.postValue("Login failed: ${response.code()}")
                }
            } catch (e: Exception) {
                loginError.postValue("Error: ${e.localizedMessage}")
            }
        }
    }
}