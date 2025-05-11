package com.example.fastafappgp.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {
    val event = MutableLiveData<EventNavigate>()

    fun OpnecartActivity(){
        event.postValue(EventNavigate.NavigateToCart)
    }

    fun OpenOrder(){
        event.postValue(EventNavigate.NavigateToOrder)
    }
    fun OpenExpiry(){
        event.postValue(EventNavigate.NavigateToExpiry)

    }

}