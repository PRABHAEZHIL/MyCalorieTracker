package com.kvep.onboarding_presentation.age

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kvep.core.domain.preference.Preference
import com.kvep.core.domain.use_case.FilterOutDigits
import com.kvep.core.util.UiEvent
import com.kvep.core.util.UiText
import com.kvep.mycalorietracker.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AgeViewModel @Inject constructor(
    private var preference: Preference,
    private var filterOutDigits: FilterOutDigits
) : ViewModel() {
    var age by mutableStateOf("20")
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onAgeEnter(age: String) {
        if (age.length <= 3) {
            this.age = filterOutDigits(age)
        }
    }

    fun onNextClick() {
        viewModelScope.launch {
            val ageNumber = age.toIntOrNull() ?:kotlin.run {
                _uiEvent.send(
                    UiEvent.ShowSnackBar(UiText.StringResource(R.string.error_age_cant_be_empty))
                )
                return@launch
            }
            preference.saveAge(ageNumber)
            _uiEvent.send(UiEvent.Success)
        }

    }
}