package com.example.quiz.data

import com.example.quiz.R
import com.example.quiz.model.Categories

class Datasource {

    //takes images and categories name from the drawable and strings from resources respectivley
    fun loadcategories(): List<Categories> {

        //returns list with the objects of Categories classes
        return listOf<Categories>(
            Categories(R.string.c_sharp, R.drawable.image_c_sharp),
            Categories(R.string.c, R.drawable.image_c),
            Categories(R.string.cpp, R.drawable.image_cpp),
            Categories(R.string.name_Java, R.drawable.image_java)
        )
    }
}