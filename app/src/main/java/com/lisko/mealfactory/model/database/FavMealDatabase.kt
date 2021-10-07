package com.lisko.mealfactory.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.lisko.mealfactory.model.entities.FavMeal

@Database(entities = [FavMeal::class], version = 1)
abstract class FavMealDatabase: RoomDatabase(){

    abstract fun favMealDao(): FavMealDao

    companion object{
            // Singleton prevents multiple instances of database opening at the
            // same time.
            @Volatile
            private var INSTANCE: FavMealDatabase? = null

            fun getDatabase(context: Context): FavMealDatabase {
                // if the INSTANCE is not null, then return it,
                // if it is, then create the database
                return INSTANCE ?: synchronized(this) {
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        FavMealDatabase::class.java,
                        "favmeal_database"
                    ).build()
                    INSTANCE = instance
                    // return instance
                    instance
                }
            }
        }

    }