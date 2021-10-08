package com.lisko.mealfactory.application

import android.app.Application
import com.lisko.mealfactory.model.database.FavMealDatabase
import com.lisko.mealfactory.model.database.FavMealRepository

class FavMealApplication : Application(){

    //lazy- created when needed, not on start
    private val database by lazy { FavMealDatabase.getDatabase(this@FavMealApplication)}
    val repository by lazy { FavMealRepository(database.favMealDao()) }

}