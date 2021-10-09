package com.lisko.mealfactory.view.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lisko.mealfactory.databinding.ItemCustomListBinding
import com.lisko.mealfactory.model.entities.FavMeal
import com.lisko.mealfactory.view.activities.AddUpdateMealActivity

class CustomListAdapter(private val activity: Activity,
                        private val listItems: List<String>,
                        private val selection: String)  : RecyclerView.Adapter<CustomListAdapter.ViewHolder>()

{
    class ViewHolder (view: ItemCustomListBinding): RecyclerView.ViewHolder(view.root){
        val tvText= view.textView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemCustomListBinding= ItemCustomListBinding.inflate(LayoutInflater.from(activity),
            parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item= listItems[position]
        holder.tvText.text= item

        holder.itemView.setOnClickListener {
            if(activity is AddUpdateMealActivity){
                activity.selectedItem(item, selection)
            }
        }

    }

    override fun getItemCount(): Int {
       return listItems.size
    }

}