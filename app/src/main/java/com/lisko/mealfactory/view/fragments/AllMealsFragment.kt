package com.lisko.mealfactory.view.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.lisko.mealfactory.R
import com.lisko.mealfactory.application.FavMealApplication
import com.lisko.mealfactory.databinding.FragmentAllMealsBinding
import com.lisko.mealfactory.view.activities.AddUpdateMealActivity
import com.lisko.mealfactory.view.adapters.FavMealAdapter
import com.lisko.mealfactory.viewmodel.AllMealsFragment
import com.lisko.mealfactory.viewmodel.FavMealViewModel
import com.lisko.mealfactory.viewmodel.FavMealViewModelFactory

class AllMealsFragment : Fragment() {

    private lateinit var allMealsFragment: AllMealsFragment
    private var _binding: FragmentAllMealsBinding? = null

    private val mFavMealViewModel: FavMealViewModel by viewModels {
        FavMealViewModelFactory((requireActivity().application
                as FavMealApplication).repository)
    }

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        allMealsFragment =
            ViewModelProvider(this).get(AllMealsFragment::class.java)

        _binding = FragmentAllMealsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvAllMealsGrid.layoutManager= GridLayoutManager(requireActivity(), 2)
        val favMealAdapter=  FavMealAdapter(this@AllMealsFragment)
        binding.rvAllMealsGrid.adapter= favMealAdapter

        mFavMealViewModel.allMealData.observe(viewLifecycleOwner){
            meals ->
                meals.let{
                    for(item in it){
                        Log.i("Meal title", "${item.id} :: ${item.title}")
                    }
                    if(it.isNotEmpty()) {
                        binding.rvAllMealsGrid.visibility= View.VISIBLE
                        binding.tvNoItems.visibility= View.GONE
                        favMealAdapter.setList(it)
                    }else{
                        binding.tvNoItems.visibility= View.VISIBLE
                        binding.rvAllMealsGrid.visibility= View.GONE
                    }
                    }
                }
        }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_all_meals, menu)
        super.onCreateOptionsMenu(menu, inflater)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.action_add_meal ->{
                startActivity(Intent(requireActivity(),AddUpdateMealActivity::class.java))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}