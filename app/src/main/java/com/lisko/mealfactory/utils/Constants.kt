package com.lisko.mealfactory.utils

object Constants {
    const val MEAL_TYPE: String= "MealType"
    const val MEAL_CATEGORY: String= "MealCategory"
    const val MEAL_COOKING_TIME: String= "MealCookingTime"
    const val MEAL_IMAGE_SOURCE_LOCAL: String= "Local"
    const val MEAL_IMAGE_SOURCE_ONLINE: String= "Online"

    fun mealTypes(): ArrayList<String>{
        val list= ArrayList<String>()
        list.add("breakfast")
        list.add("lunch")
        list.add("snack")
        list.add("dinner")
        list.add("dessert")
        return list
    }

    fun mealCategories(): ArrayList<String>{
        val list= ArrayList<String>()
        list.add("BBQ")
        list.add("Bakery")
        list.add("Cafe")
        list.add("Sandwich")
        list.add("Dessert")
        list.add("Drink")
        list.add("Wraps")

        return list
    }

    fun mealCookTime(): ArrayList<String>{
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
        return list
    }
}