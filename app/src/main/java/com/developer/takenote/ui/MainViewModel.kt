package com.developer.takenote.ui

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userSharedPreferenceRepository: SharedPreferenceRepository
) : ViewModel() {

    private val _emailId = userSharedPreferenceRepository.getMailId()
    val emailId = _emailId


}