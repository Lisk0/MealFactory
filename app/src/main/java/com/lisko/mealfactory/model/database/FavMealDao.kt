package com.lisko.mealfactory.model.database

import androidx.room.Dao
import androidx.room.Insert
import com.lisko.mealfactory.model.entities.FavMeal

@Dao
interface FavMealDao {

    @Insert
    suspend fun insertMeal(favMeal: FavMeal)


}