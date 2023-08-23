package com.kvep.tracker_presentation.tracker_overview


import androidx.annotation.DrawableRes
import com.kvep.core.util.UiText
import com.kvep.mycalorietracker.R


import com.kvep.tracker_domain.model.MealType

data class Meal(
    val name:UiText,
//    @DrawableRes val drawableRes: Int,
    val mealType: MealType,
    val carbs:Int=0,
    val protein:Int=0,
    val fat:Int=0,
    val calories:Int=0,
    val isExpanded:Boolean=false

)
val defaultMeals= listOf(
    Meal(
        name=UiText.DynamicString("Breakfast"),
        mealType = MealType.Breakfast
    ),
    Meal(
        name=UiText.DynamicString("Lunch"),
//        drawableRes = R.drawable.ic_lunch
        mealType = MealType.Lunch
    ),
    Meal(
        name=UiText.DynamicString("Dinner"),
        mealType = MealType.Dinner
    ),
    Meal(
        name=UiText.DynamicString("Snack"),
        mealType = MealType.Snack
    ),


)
