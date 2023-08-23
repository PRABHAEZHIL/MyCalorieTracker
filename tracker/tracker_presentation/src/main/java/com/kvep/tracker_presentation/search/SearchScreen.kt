package com.kvep.tracker_presentation.search

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import com.kvep.core.util.UiEvent
import com.kvep.core_ui.LocalSpacing
import com.kvep.tracker_domain.model.MealType
import com.kvep.tracker_presentation.search.components.SearchTextField
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)

@ExperimentalCoilApi
@ExperimentalComposeUiApi
@Composable
fun SearchScreen(
    scaffoldState: ScaffoldState,
    mealName: String,
    dayOfMonth: Int,
    month: Int,
    year: Int,
    onNavigateUp: () -> Unit,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val spacing = LocalSpacing.current
    val state = viewModel.state

    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(key1 = keyboardController) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.ShowSnackBarString -> {
                    scaffoldState.snackbarHostState.showSnackbar(message = event.msg)
                    keyboardController?.hide()
                }

                is UiEvent.NavigateUp -> onNavigateUp()
                else -> Unit
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(spacing.spaceMedium)
    ) {
        Text(
            text = "Add " + mealName,
            style = MaterialTheme.typography.h2
        )
        Spacer(modifier = Modifier.height(spacing.spaceMedium))
        SearchTextField(

            text = state.query,
            onValueChange = {
                Log.i("searchScreen","onvalueChange")
                viewModel.onEvent(SearchEvent.OnQueryChange(it))
                            },
            shouldShowHint = state.isHintVisible,
            onSearch = {
                keyboardController?.hide()
                viewModel.onEvent(SearchEvent.OnSearch)
                       },
            onFocusChanged = {
                viewModel.onEvent(SearchEvent.OnSearchFocusChange(it.isFocused))
            }
        )

    Spacer(modifier = Modifier.height(spacing.spaceMedium))
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(state.trackableFood) { food ->
            TrackableFoodItem(
                trackableFoodUiState = food,
                onClick = {
                    viewModel.onEvent(SearchEvent.OnToggleTrackableFood(food.food))
                },
                onAmountChange = {
                    viewModel.onEvent(SearchEvent.OnAmountForFoodChange(food.food, it))
                },
                onTrack = {
                    keyboardController?.hide()
                    viewModel.onEvent(
                        SearchEvent.OnTrackFoodClick(
                            food = food.food,
                            mealType = MealType.fromString(mealName),
                            date = LocalDate.of(year, month, dayOfMonth)
                        )
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )

        }
    }
    }
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        when {
            state.isSearching -> CircularProgressIndicator()
            state.trackableFood.isEmpty() -> {
                Text(
                    text = "No Results",
                    style = MaterialTheme.typography.body1,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}