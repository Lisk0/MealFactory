package com.lisko.mealfactory.utils

object Constants {
    const val MEAL_TYPE: String= "MealType"
    const val MEAL_CATEGORY: String= "MealCategory"
    const val MEAL_COOKING_TIME: String= "MealCookingTime"

    fun mealTypes(): ArrayList<String>{
        val list= ArrayList<String>()
        list.add("breakfast")
        list.add("lunch")
        list.add("snack")
        list.add("dinner")
        list.add("dessert")
        return list.sorted() as ArrayList<String>
    }

    fun dishCategories(): ArrayList<String>{
        val list= ArrayList<String>()
        list.add("BBQ")
        list.add("Bakery")
        list.add("Cafe")
        list.add("Sandwich")
        list.add("Dessert")
        list.add("Drink")
        list.add("Wraps")

        return list.sorted() as ArrayList<String>
    }

    fun dishCookTime(): ArrayList<String>{
        val list= ArrayList<String>()
        list.add("10")
        list.add("15")
        list.add("20")
        list.add("30")
        list.add("45")
        list.add("50")
        list.add("60")
        list.add("90")
        list.add("120")
        list.add("150")
        return list.sorted() as ArrayList<String>
    }
}