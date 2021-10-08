package com.lisko.mealfactory.model.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.lisko.mealfactory.model.entities.FavMeal
import kotlinx.coroutines.flow.Flow

@Dao
interface FavMealDao {

    @Insert
    suspend fun insertMeal(favMeal: FavMeal)

    @Query("select * from favmeal_database order by id")
    fun getAllMeals(): Flow<List<FavMeal>>

}