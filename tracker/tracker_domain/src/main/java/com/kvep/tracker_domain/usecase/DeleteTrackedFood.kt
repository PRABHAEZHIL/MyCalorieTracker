package com.kvep.tracker_domain.usecase

import com.kvep.tracker_domain.model.TrackedFood
import com.kvep.tracker_domain.repository.TrackerRepository

class DeleteTrackedFood(private val repository: TrackerRepository) {
    suspend operator fun invoke(
        food: TrackedFood
    ){
        repository.deleteTrackedFood(food)
    }
}