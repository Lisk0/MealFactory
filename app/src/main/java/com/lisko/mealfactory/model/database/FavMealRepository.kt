package com.lisko.mealfactory.model.database

import androidx.annotation.WorkerThread
import com.lisko.mealfactory.model.entities.FavMeal

class FavMealRepository (private val favMealDao: FavMealDao){

    @WorkerThread
    suspend fun insertFavMeal(favMeal: FavMeal){
        favMealDao.insertMeal(favMeal)
    }
}