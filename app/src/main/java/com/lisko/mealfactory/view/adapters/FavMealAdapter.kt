package com.lisko.mealfactory.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lisko.mealfactory.databinding.ItemCustomListBinding
import com.lisko.mealfactory.databinding.ItemMealGridBinding
import com.lisko.mealfactory.model.entities.FavMeal
import com.lisko.mealfactory.view.fragments.AllMealsFragment

class FavMealAdapter(private val fragment: Fragment ) : RecyclerView.Adapter<FavMealAdapter.ViewHolder>() {

    private var mMealList: List<FavMeal> = listOf()

    class ViewHolder(view: ItemMealGridBinding): RecyclerView.ViewHolder(view.root){
        val ivMealImageGrid= view.ivMealImageGrid
        val tvDishTitleGrid= view.tvDishTitleGrid
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavMealAdapter.ViewHolder {
        val binding: ItemMealGridBinding = ItemMealGridBinding.inflate(
            LayoutInflater.from(fragment.context),
            parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavMealAdapter.ViewHolder, position: Int) {
        val meal= mMealList[position]
        Glide.with(fragment).load(meal.image).into(holder.ivMealImageGrid)
        holder.tvDishTitleGrid.text= meal.title
        holder.itemView.setOnClickListener{
            if (fragment is AllMealsFragment){
                fragment.dishDetails(meal.id)
            }
        }
    }

    override fun getItemCount(): Int {
        return mMealList.size
    }

    fun setList(list: List<FavMeal>){
        mMealList= list
        notifyDataSetChanged()
    }
}