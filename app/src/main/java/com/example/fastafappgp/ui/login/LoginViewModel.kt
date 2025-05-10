package com.example.fastafappgp.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fastafappgp.apimanager.ApiManager
import com.example.fastafappgp.util.SingleLiveEvent
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    val username = MutableLiveData<String>("mustafa_hany")
    val password = MutableLiveData<String>("password")

    val loginResponse = MutableLiveData<LoginResponse>()
    val loginError = MutableLiveData<String>()
    val isLoading = MutableLiveData<Boolean>(false)

    val event = SingleLiveEvent<NavigateEvent>()

    fun login() {
        val user = username.value ?: ""
        val pass = password.value ?: ""


        when {
            user.isBlank() -> {
                loginError.postValue("Username cannot be empty")
                return
            }
            pass.isBlank() -> {
                loginError.postValue("Password cannot be empty")
                return
            }
            pass.length < 6 -> {
                loginError.postValue("Password must be at least 6 characters")
                return
            }
        }


        isLoading.postValue(true)

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
                        loginError.postValue("Server returned empty response")
                    }
                } else {

                    val errorMessage = when (response.code()) {
                        401 -> "Invalid username or password"
                        403 -> "Access denied"
                        404 -> "Service not found"
                        500 -> "Server error"
                        else -> "Login failed: ${response.code()}"
                    }
                    loginError.postValue(errorMessage)
                }
            } catch (e: Exception) {

                val errorMessage = when (e) {
                    is java.net.UnknownHostException -> "No internet connection"
                    is java.net.SocketTimeoutException -> "Connection timeout"
                    else -> "Error: ${e.localizedMessage}"
                }
                loginError.postValue(errorMessage)
            } finally {
                isLoading.postValue(false)
            }
        }
    }
}