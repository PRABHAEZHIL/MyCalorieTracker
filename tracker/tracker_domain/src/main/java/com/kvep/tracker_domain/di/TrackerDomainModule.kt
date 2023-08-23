package com.kvep.tracker_domain.di

import com.kvep.core.domain.preference.Preference
import com.kvep.tracker_domain.repository.TrackerRepository
import com.kvep.tracker_domain.usecase.CalculateMealNutrients
import com.kvep.tracker_domain.usecase.DeleteTrackedFood
import com.kvep.tracker_domain.usecase.GetFoodsForDate
import com.kvep.tracker_domain.usecase.SearchFood
import com.kvep.tracker_domain.usecase.TrackFood
import com.kvep.tracker_domain.usecase.TrackerUsecases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object TrackerDomainModule {

    @ViewModelScoped
    @Provides
    fun provideTrackerUseCases(repository: TrackerRepository,preference: Preference):TrackerUsecases{
        return TrackerUsecases(
            trackFood = TrackFood(repository),
            searchFood = SearchFood(repository),
            getFoodsForDate = GetFoodsForDate(repository),
            deleteTrackedFood = DeleteTrackedFood(repository),
            calculateMealNutrients = CalculateMealNutrients(preference)
        )
    }
}