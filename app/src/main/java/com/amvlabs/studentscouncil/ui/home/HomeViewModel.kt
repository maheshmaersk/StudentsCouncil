package com.amvlabs.studentscouncil.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Complains Fragment"
    }


    val text: LiveData<String> = _text
}