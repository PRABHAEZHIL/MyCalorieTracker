package com.kvep.tracker_presentation.search

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kvep.core.domain.use_case.FilterOutDigits
import com.kvep.core.util.UiEvent
import com.kvep.tracker_domain.usecase.TrackerUsecases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val trackerUsecases: TrackerUsecases,
    private val filterOutDigits: FilterOutDigits
) : ViewModel() {
    var state by mutableStateOf(SearchState())
        private set
    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.OnQueryChange -> {
                state=state.copy(query=event.query)
            }

            is SearchEvent.OnSearch -> {
                Log.i("search","viewmodelSearch")
                 executeSearch()
            }

            is SearchEvent.OnSearchFocusChange -> {
                state=state.copy(
                    isHintVisible = !event.isFocused && state.query.isBlank()
                )
            }

            is SearchEvent.OnTrackFoodClick -> {
                trackFood(event)

            }

            is SearchEvent.OnAmountForFoodChange -> {
                state=state.copy(
                    trackableFood = state.trackableFood.map {
                        if(it.food==event.food){
                            it.copy(amount =filterOutDigits( event.amount))
                        }else it
                    }
                )
            }

            is SearchEvent.OnToggleTrackableFood -> {
                state=state.copy(
                trackableFood = state.trackableFood.map {
                    if(it.food==event.food){
                        it.copy(isExpanded = !it.isExpanded)
                    }else it
                }
                )
            }
        }
    }

    private fun executeSearch() {
        Log.i("search","executeSearch")
        viewModelScope.launch {
            state=state.copy(
                isSearching = true,
                trackableFood = emptyList()
            )
            trackerUsecases
                .searchFood(state.query)
                .onSuccess { foods->
                    state=state.copy(
                        trackableFood = foods.map {
                            TrackableFoodUiState(it)
                        },
                        isSearching = false,
                        query=""
                    )
                }
                .onFailure {
                    state=state.copy(isSearching = false)
                    _uiEvent.send(
                        UiEvent.ShowSnackBarString("Error Something Went Wrong")
                    )
                }
        }
    }

    private fun trackFood(event:SearchEvent.OnTrackFoodClick) {
       viewModelScope.launch {
            val uiState=state.trackableFood.find{it.food==event.food}
           trackerUsecases.trackFood(
               food = uiState?.food?:return@launch,
               amount = uiState.amount.toIntOrNull()?:return@launch,
               mealType = event.mealType,
               date=event.date
           )
           _uiEvent.send(UiEvent.NavigateUp)
       }
    }

}