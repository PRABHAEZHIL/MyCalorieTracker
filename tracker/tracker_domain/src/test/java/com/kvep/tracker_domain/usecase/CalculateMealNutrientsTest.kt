package com.kvep.tracker_domain.usecase

import com.kvep.core.domain.model.ActivityLevel
import com.google.common.truth.Truth.assertThat

import com.kvep.core.domain.model.Gender
import com.kvep.core.domain.model.GoalType
import com.kvep.core.domain.model.UserInfo
import com.kvep.core.domain.preference.Preference
import com.kvep.tracker_domain.model.MealType
import com.kvep.tracker_domain.model.TrackedFood
import io.mockk.every
import io.mockk.mockk
import org.junit.Before
import org.junit.Test
import java.time.LocalDate
import kotlin.random.Random

class CalculateMealNutrientsTest {
    private lateinit var calculateMealNutrients: CalculateMealNutrients

    @Before
    fun setUp(){
        val preference=mockk<Preference>(relaxed=true)
        every {
            preference.loadUserInfo()
        }returns UserInfo(
            gender= Gender.Male,
            age=20,
            weight = 80f,
            height=180,
            activityLevel = ActivityLevel.Medium,
            goalType = GoalType.LoseWeight,
            carbRatio=0.4f,
            proteinRatio = 0.3f,
            fatRatio = 0.3f
        )
        calculateMealNutrients= CalculateMealNutrients(preference)
    }

    @Test
    fun `Calories for breakfast properly calculated`(){
        val trackedFood=(1..30).map{
            TrackedFood(
                name="name",
                carbs= Random.nextInt(100),
                protein=Random.nextInt(100),
                fat=Random.nextInt(100),
                mealType= MealType.fromString(
                    listOf("breakfast","lunch","dinner","snack").random()
                ),
                imageUrl = null,
                amount=100,
                date= LocalDate.now(),
                calories=Random.nextInt(2000)
            )
        }
        val result=calculateMealNutrients(trackedFood)

        val breakfastCalories=result.mealNutrients.values.filter {
            it.mealType is MealType.Breakfast
        }
            .sumOf { it.calories }
        val expectedCalories=trackedFood.filter {
            it.mealType is MealType.Breakfast
        }
            .sumOf { it.calories }

        assertThat(breakfastCalories).isEqualTo(expectedCalories)
    }
    @Test
    fun `carbs for dinner properly calculated`(){
        val trackedFood=(1..30).map{
            TrackedFood(
                name="name",
                carbs= Random.nextInt(100),
                protein=Random.nextInt(100),
                fat=Random.nextInt(100),
                mealType= MealType.fromString(
                    listOf("breakfast","lunch","dinner","snack").random()
                ),
                imageUrl = null,
                amount=100,
                date= LocalDate.now(),
                calories=Random.nextInt(2000)
            )
        }
        val result=calculateMealNutrients(trackedFood)

        val dinnerCalories=result.mealNutrients.values.filter {
            it.mealType is MealType.Dinner
        }
            .sumOf { it.carbs }
        val expectedCalories=trackedFood.filter {
            it.mealType is MealType.Dinner
        }
            .sumOf { it.carbs }

        assertThat(dinnerCalories).isEqualTo(expectedCalories)
    }

}