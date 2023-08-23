package com.kvep.core.data.preferences

import android.content.SharedPreferences
import com.kvep.core.domain.model.ActivityLevel
import com.kvep.core.domain.model.Gender
import com.kvep.core.domain.model.GoalType
import com.kvep.core.domain.model.UserInfo
import com.kvep.core.domain.preference.Preference


class DefaultPreferences(
    private val sharedPref: SharedPreferences
):Preference {
    override fun saveGender(gender: Gender) {
            sharedPref.edit()
                .putString(Preference.KEY_GENDER,gender.name)
                .apply()
    }

    override fun saveAge(age: Int) {
        sharedPref.edit()
            .putInt(Preference.KEY_AGE,age)
            .apply()
    }

    override fun saveWeight(weight: Float) {
        sharedPref.edit()
            .putFloat(Preference.KEY_WEIGHT,weight)
            .apply()
    }

    override fun saveHeight(height: Int) {
        sharedPref.edit()
            .putInt(Preference.KEY_HEIGHT,height)
            .apply()
    }

    override fun saveActivityLevel(activityLevel: ActivityLevel) {
        sharedPref.edit()
            .putString(Preference.KEY_ACTIVITY_LEVEL,activityLevel.name)
            .apply()
    }

    override fun saveGoalType(goalType: GoalType) {
        sharedPref.edit()
            .putString(Preference.KEY_GOAL_TYPE,goalType.name)
            .apply()
    }

    override fun saveCarbRatio(carbRatio: Float) {
        sharedPref.edit()
            .putFloat(Preference.KEY_CARB_RATIO,carbRatio)
            .apply()
    }

    override fun saveProteinRatio(proteinRatio: Float) {
        sharedPref.edit()
            .putFloat(Preference.KEY_PROTEIN_RATIO,proteinRatio)
            .apply()
    }

    override fun saveFatRatio(fatRatio: Float) {
        sharedPref.edit()
            .putFloat(Preference.KEY_FAT_RATIO,fatRatio)
            .apply()
    }

    override fun loadUserInfo(): UserInfo {
        val age=sharedPref.getInt(Preference.KEY_AGE,-1)
        val height=sharedPref.getInt(Preference.KEY_HEIGHT,-1)
        val weight=sharedPref.getFloat(Preference.KEY_WEIGHT,-1f)
        val genderString=sharedPref.getString(Preference.KEY_GENDER,null)
        val activityLevelString=sharedPref.getString(Preference.KEY_ACTIVITY_LEVEL,null)
        val goalType=sharedPref.getString(Preference.KEY_GOAL_TYPE,null)
        val carbRatio=sharedPref.getFloat(Preference.KEY_CARB_RATIO,-1f)
        val proteinRatio=sharedPref.getFloat(Preference.KEY_PROTEIN_RATIO,-1f)
        val fatRatio=sharedPref.getFloat(Preference.KEY_FAT_RATIO,-1f)

        return UserInfo(
            gender= Gender.fromString(genderString?:"male"),
            age=age,
            weight=weight,
            height=height,
            activityLevel = ActivityLevel.fromString(activityLevelString?:"medium"),
            goalType=GoalType.fromString(goalType?:"keep_weight"),
            proteinRatio=proteinRatio,
            fatRatio=fatRatio,
          carbRatio=carbRatio

        )

    }

    override fun saveShouldShowOnboarding(shouldShow: Boolean) {
        sharedPref.edit()
            .putBoolean(Preference.KEY_SHOULD_SHOW_ONBOARDING,shouldShow)
            .apply()
    }

    override fun loadShouldShowOnboarding(): Boolean {
        return sharedPref.getBoolean(Preference.KEY_SHOULD_SHOW_ONBOARDING,true)
    }
}