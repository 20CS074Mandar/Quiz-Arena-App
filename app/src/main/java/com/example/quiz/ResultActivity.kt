package com.example.quiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.quiz.databinding.ActivityResultBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var user: FirebaseUser

    var Score:String=""

    override fun onCreate(savedInstanceState: Bundle?) {
        binding= ActivityResultBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        user = auth.currentUser!!


        displayScore()

        binding.finishButton.setOnClickListener{
            startActivity(Intent(this,CategoriesActivity::class.java))
            finish()
        }

    }

    public fun displayScore()
    {
        val uid=auth.uid;
        val userRef=FirebaseDatabase.getInstance().getReference("Users")
        userRef.child("${uid}")
            .addValueEventListener(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    Score=snapshot.child("User Score").getValue().toString()
                    binding.scoreTv.text="Your Score is ${Score}/10"
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
    }

}