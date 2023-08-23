package com.kvep.mycalorietracker

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.annotation.ExperimentalCoilApi
import com.kvep.mycalorietracker.navigation.Route
import com.kvep.core.domain.preference.Preference
import com.kvep.mycalorietracker.ui.theme.MyCalorieTrackerTheme
import com.kvep.onboarding_presentation.activity.ActivityScreen
import com.kvep.onboarding_presentation.age.AgeScreen
import com.kvep.onboarding_presentation.gender.GenderScreen
import com.kvep.onboarding_presentation.goal.GoalScreen
import com.kvep.onboarding_presentation.height.HeightScreen
import com.kvep.onboarding_presentation.nutrient_goal.NutrientGoalScreen
import com.kvep.onboarding_presentation.weight.WeightScreen
import com.kvep.onboarding_presentation.welcome.WelcomeScreen
import com.kvep.tracker_presentation.search.SearchScreen
import com.kvep.tracker_presentation.tracker_overview.TrackerOverviewScreen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@ExperimentalComposeUiApi
@ExperimentalCoilApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var preference: Preference
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val shouldShowOnBoarding=preference.loadShouldShowOnboarding()
        setContent {
            MyCalorieTrackerTheme {
                // A surface container using the 'background' color from the theme
//                WelcomeScreen()
                val navController = rememberNavController()
                val scaffoldState = rememberScaffoldState()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    scaffoldState = scaffoldState
                ) {
                    NavHost(
                        navController = navController,
                        startDestination =if(shouldShowOnBoarding){
                            Route.WELCOME
                        }else Route.TRACKER_OVERVIEW
                    )
                    {
                        composable(Route.WELCOME) {
                            WelcomeScreen(onNextClick = {
                                navController.navigate(Route.GENDER)
                            })
                        }
                        composable(Route.AGE) {
                            AgeScreen(
                                scaffoldState = scaffoldState,
                                onNextClick = {
                                    navController.navigate(Route.HEIGHT)
                                }
                            )
                        }
                        composable(Route.GOAL) {
                            GoalScreen(onNextClick = {
                                navController.navigate(Route.NUTRIENT_GOAL)
                            })
                        }
                        composable(Route.GENDER) {
                            GenderScreen(onNextClick = {
                                navController.navigate(Route.AGE)
                            })
                        }
                        composable(Route.NUTRIENT_GOAL) {
                            NutrientGoalScreen(
                                onNextClick = {
                                    navController.navigate(Route.TRACKER_OVERVIEW)
                                },
                                scaffoldState = scaffoldState
                            )
                        }
                        composable(
                            route = Route.SEARCH + "/{mealName}/{dayOfMonth}/{month}/{year}",
                            arguments = listOf(
                                navArgument("mealName") {
                                    type = NavType.StringType
                                },
                                navArgument("dayOfMonth") {
                                    type = NavType.IntType
                                },
                                navArgument("month") {
                                    type = NavType.IntType
                                },
                                navArgument("year") {
                                    type = NavType.IntType
                                }

                            )

                        ) {
                            val mealName = it.arguments?.getString("mealName")!!
                            val dayOfMonth = it.arguments?.getInt("dayOfMonth")!!
                            val month = it.arguments?.getInt("month")!!
                            val year = it.arguments?.getInt("year")!!
                            SearchScreen(
                                scaffoldState = scaffoldState,
                                mealName = mealName,
                                dayOfMonth = dayOfMonth,
                                month = month,
                                year = year,
                                onNavigateUp = {
                                    navController.navigateUp()
                                })
                        }
                        composable(Route.TRACKER_OVERVIEW) {
                            TrackerOverviewScreen(
                                onNavigateToSearch = {
                                    mealName,day,month,year->
                                    navController.navigate(
                                        Route.SEARCH + "/$mealName" +
                                                "/$day" +
                                                "/$month" +
                                                "/$year"
                                    )
                                }
                            )
                        }
                        composable(Route.WEIGHT) {
                            WeightScreen(
                                onNextClick = {
                                    navController.navigate(Route.ACTIVITY)
                                },
                                scaffoldState = scaffoldState
                            )
                        }

                        composable(Route.HEIGHT) {
                            HeightScreen(
                                onNextClick = {
                                    navController.navigate(Route.WEIGHT)
                                },
                                scaffoldState = scaffoldState
                            )
                        }
                        composable(Route.ACTIVITY) {
                            ActivityScreen(onNextClick = {
                                navController.navigate(Route.GOAL)
                            })
                        }
                    }
                }

            }
        }
    }
}

