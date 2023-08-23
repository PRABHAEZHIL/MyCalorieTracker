package com.kvep.onboarding_presentation.nutrient_goal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.kvep.core.util.UiEvent
import com.kvep.core_ui.LocalSpacing
import com.kvep.mycalorietracker.R
import com.kvep.onboarding_presentation.components.ActionButton
import com.kvep.onboarding_presentation.components.UnitTextField

@Composable
fun NutrientGoalScreen(
    onNextClick: () -> Unit,
    scaffoldState: ScaffoldState,
    viewModel: NutrientGoalViewModel = hiltViewModel()
) {
    val spacing = LocalSpacing.current
    var context = LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect() {
            when (it) {
                is UiEvent.Success -> onNextClick()
                is UiEvent.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = it.msg.asString(context)
                    )
                }

                else -> Unit
            }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(spacing.spaceLarge)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Text(
                text = stringResource(R.string.what_are_your_nutrient_goals),
                style = MaterialTheme.typography.h3
            )
            Spacer(modifier = Modifier.height(spacing.spaceMedium))
            UnitTextField(
                value = viewModel.state.carbRatio,
                onValueChanged = {
                    viewModel.onEvent(NutrientGoalEvent.onCrabRatioEnter(it))
                },
                unit = stringResource(
                    id = R.string.carbs
                )
            )
            Spacer(modifier = Modifier.height(spacing.spaceMedium))
            UnitTextField(
                value = viewModel.state.proteinRatio,
                onValueChanged = {
                    viewModel.onEvent(NutrientGoalEvent.onProteinRatioEnter(it))
                },
                unit = stringResource(
                    id = R.string.protein
                )
            )
            Spacer(modifier = Modifier.height(spacing.spaceMedium))
            UnitTextField(
                value = viewModel.state.fatRatio,
                onValueChanged = {
                    viewModel.onEvent(NutrientGoalEvent.onFatRatioEnter(it))
                },
                unit = stringResource(
                    id = R.string.fat
                )
            )
        }
        ActionButton(
            text = stringResource(id = R.string.next),
            onClick = { viewModel.onEvent(NutrientGoalEvent.OnNextClick) },
            modifier = Modifier.align(
                Alignment.BottomEnd
            )
        )
    }

}