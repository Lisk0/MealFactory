package com.lisko.mealfactory.viewmodel

import androidx.lifecycle.*
import com.lisko.mealfactory.model.database.FavMealRepository
import com.lisko.mealfactory.model.entities.FavMeal
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class FavMealViewModel(private val repository: FavMealRepository) : ViewModel() {
    fun insert(favMeal: FavMeal)= viewModelScope.launch {
        repository.insertFavMeal(favMeal)
    }

    val allMealData: LiveData<List<FavMeal>> = repository.allMealList.asLiveData()

}

class FavMealViewModelFactory(private val repository: FavMealRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(FavMealViewModel::class.java))
            return FavMealViewModel(repository) as T
        throw IllegalArgumentException("Unknown class")
    }

}