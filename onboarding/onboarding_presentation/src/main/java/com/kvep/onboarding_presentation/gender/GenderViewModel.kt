package com.kvep.onboarding_presentation.gender


import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kvep.core.domain.model.Gender
import com.kvep.core.domain.preference.Preference
import com.kvep.core.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GenderViewModel @Inject constructor(
    private val preference: Preference
) : ViewModel() {
    var selectedGender by mutableStateOf<Gender>(Gender.Female)
        private set
    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onGenderClick(gender: Gender) {
        Log.i("MyTage", "selected Gender $gender")
        selectedGender = gender
        println("Gender:")
    }

    fun onNextClick() {
        viewModelScope.launch {
            preference.saveGender(selectedGender)
            _uiEvent.send(UiEvent.Success)
        }
    }

}