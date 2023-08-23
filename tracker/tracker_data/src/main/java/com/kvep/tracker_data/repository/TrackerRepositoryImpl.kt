package com.kvep.tracker_data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.kvep.tracker_data.remote.OpenFoodApi
import com.kvep.tracker_data.local.TrackerDao
import com.kvep.tracker_data.mappers.toTrackableFood
import com.kvep.tracker_data.mappers.toTrackedFood
import com.kvep.tracker_data.mappers.toTrackedFoodEntity
import com.kvep.tracker_domain.model.TrackableFood
import com.kvep.tracker_domain.model.TrackedFood
import com.kvep.tracker_domain.repository.TrackerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.lang.Exception
import java.time.LocalDate

class TrackerRepositoryImpl(
    private val api: OpenFoodApi,
    private val dao: TrackerDao
) : TrackerRepository {
    override suspend fun searchFood(
        query: String,
        page: Int,
        pageSize: Int
    ): Result<List<TrackableFood>> {
        return try {
            val searchDto = api.searchFood(
                query = query,
                page = page,
                pageSize = pageSize
            )
            Result.success(searchDto.products
                .filter {
                    val calculatedCalories = it.nutriments.carbohydrates100g * 4f +
                            it.nutriments.proteins100g * 4f + it.nutriments.fat100g * 9f
                    val lowerBound = calculatedCalories * 0.99f
                    val upperBound = calculatedCalories * 1.01f
                    it.nutriments.energyKcal100g in (lowerBound..upperBound)
                }
                .mapNotNull { it.toTrackableFood() })
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun insertTrackedFood(food: TrackedFood) {
        dao.insertTrackedFood(food.toTrackedFoodEntity())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun deleteTrackedFood(food: TrackedFood) {
        dao.deleteTrackedFood(food.toTrackedFoodEntity())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getFoodsForDate(localDate: LocalDate): Flow<List<TrackedFood>> {
        return dao.getFoodForDate(
            day = localDate.dayOfMonth,
            month = localDate.monthValue,
            year = localDate.year
        ).map { entities ->
            entities.map { it.toTrackedFood() }
        }
    }
}