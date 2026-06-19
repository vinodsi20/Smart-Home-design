package com.example.smarthomedesign.viewmodel

import androidx.lifecycle.ViewModel
import com.example.smarthomedesign.model.UserProfile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class UserViewModel : ViewModel() {
    private val _userProfile = MutableStateFlow(UserProfile())
    val userProfile: StateFlow<UserProfile> = _userProfile.asStateFlow()

    fun updateProfile(name: String, email: String, phone: String, address: String) {
        _userProfile.update {
            it.copy(name = name, email = email, phone = phone, address = address)
        }
    }
}
