package com.example.quiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.quiz.adapter.CategoryAdapter
import com.example.quiz.data.Datasource
import com.example.quiz.databinding.ActivityCategoriesBinding

class CategoriesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCategoriesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityCategoriesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showCategories()
    }

    private fun showCategories()
    {
        //getting the categories aded to the list in DATASource class->loadCategories()
        val categoryList=Datasource().loadcategories()
        //adapter for recucler view
        binding.categoriesRv.adapter = CategoryAdapter(this, categoryList)

        //setting the grid laout type to the recuclerview
        binding.categoriesRv.layoutManager = GridLayoutManager(this@CategoriesActivity, 2)
        binding.categoriesRv.setHasFixedSize(true)
    }

}