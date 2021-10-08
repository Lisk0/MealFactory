package com.lisko.mealfactory.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.lisko.mealfactory.model.database.FavMealRepository
import com.lisko.mealfactory.model.entities.FavMeal
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class FavMealViewModel(private val repository: FavMealRepository) : ViewModel() {
    fun insert(favMeal: FavMeal)= viewModelScope.launch {
        repository.insertFavMeal(favMeal)
    }

}

class FavMealViewModelFactory(private val repository: FavMealRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(FavMealViewModel::class.java))
            return FavMealViewModel(repository) as T
        throw IllegalArgumentException("Unknown class")
    }

}