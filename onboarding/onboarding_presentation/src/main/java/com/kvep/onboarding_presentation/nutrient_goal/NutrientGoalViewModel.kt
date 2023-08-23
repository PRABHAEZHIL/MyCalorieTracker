package com.kvep.onboarding_presentation.nutrient_goal

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kvep.core.domain.preference.Preference
import com.kvep.core.domain.use_case.FilterOutDigits
import com.kvep.core.util.UiEvent
import com.kvep.onboarding_domain.usecase.ValidateNutrients
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NutrientGoalViewModel @Inject constructor(
    private val preference: Preference,
    private val filterOutDigits: FilterOutDigits,
    private val validateNutrients: ValidateNutrients
):ViewModel() {
    var state by mutableStateOf(NutrientGoalState())
        private set

    private val _uiEvent= Channel<UiEvent>()
    val uiEvent=_uiEvent.receiveAsFlow()

    fun onEvent(event: NutrientGoalEvent){
        when(event){
            is NutrientGoalEvent.onCrabRatioEnter->{
                    state=state.copy(carbRatio = filterOutDigits(event.ratio))
            }
            is NutrientGoalEvent.onProteinRatioEnter->{
                    state=state.copy(proteinRatio = filterOutDigits(event.ratio))
            }
            is NutrientGoalEvent.onFatRatioEnter->{
                    state=state.copy(fatRatio = filterOutDigits(event.ratio))
            }
            is NutrientGoalEvent.OnNextClick->{
                    val result=validateNutrients(
                        carbsRatioText = state.carbRatio,
                        proteinRatioText = state.proteinRatio,
                        fatRatioText = state.fatRatio
                    )
                when(result){
                    is ValidateNutrients.Results.Success->{
                        preference.saveCarbRatio(result.carbsRatio)
                        preference.saveProteinRatio(result.proteinRatio)
                        preference.saveFatRatio(result.fatRatio)
                        viewModelScope.launch {
                            _uiEvent.send(UiEvent.Success)
                        }
                    }
                    is ValidateNutrients.Results.Error->{
                        viewModelScope.launch {
                            _uiEvent.send(UiEvent.ShowSnackBar(result.message))
                        }

                    }
                }
            }
        }
    }
}