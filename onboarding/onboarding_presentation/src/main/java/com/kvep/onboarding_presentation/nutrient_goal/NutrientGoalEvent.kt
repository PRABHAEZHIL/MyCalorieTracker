package com.kvep.onboarding_presentation.nutrient_goal

sealed class NutrientGoalEvent {
        data class onCrabRatioEnter(val ratio:String):NutrientGoalEvent()
        data class onProteinRatioEnter(val ratio:String):NutrientGoalEvent()
        data class onFatRatioEnter(val ratio:String):NutrientGoalEvent()
        object OnNextClick:NutrientGoalEvent()
}