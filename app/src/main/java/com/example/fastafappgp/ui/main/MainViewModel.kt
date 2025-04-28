package com.example.fastafappgp.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {
    val event = MutableLiveData<EventNavigate>()

    fun OpenCamActivity(){
        event.postValue(EventNavigate.NavigateToCam)
    }

    fun Opensearch(){
        event.postValue(EventNavigate.NavigateToSearch)

    }
}