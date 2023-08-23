package com.kvep.core.domain.model

data class UserInfo(
    val gender: Gender,
    val age: Int,
    val weight: Float,
    val height: Int,
    val activityLevel: ActivityLevel,
    val goalType: GoalType,
    val proteinRatio: Float,
    val fatRatio: Float,
    val carbRatio:Float

)