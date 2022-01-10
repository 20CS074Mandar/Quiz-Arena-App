package com.example.quiz.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.quiz.QuestionsActivity
import com.example.quiz.databinding.CategoriesDesignBinding
import com.example.quiz.model.Categories

class CategoryAdapter(val context: Context,val categoryList: List<Categories>) :RecyclerView.Adapter<CategoryAdapter.CategoryHolder>(){

    class CategoryHolder(val binding: CategoriesDesignBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHolder {

        //returs the view to be created by view Binding using the layoutinflater for card design file
        return CategoryHolder(CategoriesDesignBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: CategoryHolder, position: Int) {

        //gets category from the categoryList LIst
        val category=categoryList[position]
        val categoryname=context.resources.getString(category.stringResourceId)
        holder.binding.categoriesName.text=categoryname
        holder.binding.categoriesImage.setImageResource(category.imageResourceid)

        holder.binding.cardView.setOnClickListener{
            val i=Intent(context,QuestionsActivity::class.java)
            i.putExtra("categoryname",categoryname)
            context.startActivity(i)
        }
    }

    override fun getItemCount(): Int =categoryList.size

}