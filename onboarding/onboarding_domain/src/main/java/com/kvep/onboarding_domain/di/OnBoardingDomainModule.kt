package com.kvep.onboarding_domain.di

import com.kvep.onboarding_domain.usecase.ValidateNutrients
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object  OnBoardingDomainModule{
    @Provides
    @ViewModelScoped
    fun provideValidateNutrientsUseCase():ValidateNutrients{
        return ValidateNutrients()
    }

}
