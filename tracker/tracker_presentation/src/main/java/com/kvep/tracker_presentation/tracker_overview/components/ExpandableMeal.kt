package com.kvep.tracker_presentation.tracker_overview.components

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import com.kvep.core_ui.LocalSpacing
import com.kvep.tracker_presentation.components.NutrientInfo
import com.kvep.tracker_presentation.components.UnitDisplay
import com.kvep.tracker_presentation.tracker_overview.Meal

@Composable
fun ExpandableMeal(
    meal: Meal,
    onToggleClick: () -> Unit,
    content: @Composable () -> Unit,
    modifier: Modifier
) {
    val spacing = LocalSpacing.current
    val context= LocalContext.current
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onToggleClick() }
                .padding(spacing.spaceMedium),
            verticalAlignment = Alignment.CenterVertically

        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                   Text(
                       text=meal.name.asString(context),
                        style= MaterialTheme.typography.h3
                   )
                    Icon(
                        imageVector=if(meal.isExpanded){
                            Icons.Default.KeyboardArrowUp
                        }else Icons.Default.KeyboardArrowDown,
                        contentDescription = if(meal.isExpanded){
                            "Collapse"
                        }else{"Extend"}
                    )
                }
                Spacer(modifier=Modifier.height(spacing.spaceSmall))
                Row(
                    modifier=Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    UnitDisplay(
                        amount = meal.calories,
                        unit = "kcal",
                        amountTextSize=30.sp
                    )
                    Row{

                       NutrientInfo(
                           name ="Carbs",
                           amount =meal.carbs,
                           unit ="g"
                       )
                        Spacer(modifier=Modifier.width(spacing.spaceSmall))
                        NutrientInfo(
                            name ="Protein",
                            amount =meal.protein,
                            unit ="g"
                        )
                        Spacer(modifier=Modifier.width(spacing.spaceSmall))
                        NutrientInfo(
                            name ="Fat",
                            amount =meal.fat,
                            unit ="g"
                        )

                    }
                }
            }
        }
        Spacer(modifier=Modifier.height(spacing.spaceMedium))
        AnimatedVisibility(visible = meal.isExpanded) {
            Log.i("hi","ji")
            content()

        }
    }
}