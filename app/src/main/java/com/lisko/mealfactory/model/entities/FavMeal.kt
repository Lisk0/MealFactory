package com.lisko.mealfactory.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favmeal_table")
data class FavMeal (
    @ColumnInfo val image: String,
    @ColumnInfo val imageSource: String,
    @ColumnInfo val title: String,
    @ColumnInfo val type: String,
    @ColumnInfo val category: String,
    @ColumnInfo val ingredients: String,
    @ColumnInfo val cookingTime: String,
    @ColumnInfo val steps: String,
    @ColumnInfo val favouriteMeal: Boolean,
    @PrimaryKey(autoGenerate = true) val id: Int=0
        )