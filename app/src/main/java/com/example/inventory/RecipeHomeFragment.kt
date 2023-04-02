package com.example.inventory

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import androidx.room.Room
import com.example.inventory.MultiSelectSpinnerAdapter
import com.example.inventory.data.Item
import com.example.inventory.databinding.FragmentRecipeHomeBinding
import com.example.inventory.databinding.ItemListFragmentBinding

class RecipeHomeFragment : Fragment() {

    private val viewModel: InventoryViewModel by activityViewModels {
        InventoryViewModelFactory(
            (activity?.application as InventoryApplication).database.itemDao(),
            (activity?.application as InventoryApplication).database.labelDao()
        )
    }
    private lateinit var spinner: Spinner
    private lateinit var itemNameList: List<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_recipe_home, container, false)
        spinner = view.findViewById(R.id.ingredients_selected_for_recipe_search)

        val itemLiveData: LiveData<List<String>> = viewModel.getAllUniqueNames()
        itemLiveData.observe(viewLifecycleOwner) { names ->
            itemNameList = names
            val adapter = MultiSelectSpinnerAdapter(requireContext(), itemNameList)
            spinner.adapter = adapter
        }
        val options = listOf("Option 1", "Option 2", "Option 3", "Option 4")
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val spinner: Spinner = view.findViewById(R.id.ingredients_selected_for_recipe_search)
        val submit: Button = view.findViewById(R.id.testingSubmit)
        submit.setOnClickListener {
            val itemms = getSelectedItems()
            for (item in itemms) {
                Log.d("item", item)
            }
            Log.d("lol", "lool")
        }

    }

//    testingSubmit
    fun getSelectedItems(): List<String> {
        return (spinner.adapter as MultiSelectSpinnerAdapter).getSelectedItems()
    }

     fun doSomethingWithItems() {
         // Iterate over the itemList and do something with each item
         for (item in itemNameList) {
             Log.d("itemName", item)
         }
     }
}