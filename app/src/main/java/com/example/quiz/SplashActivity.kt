package com.example.quiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.quiz.databinding.ActivitySplashBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SplashActivity : AppCompatActivity() {

    private lateinit var binding:ActivitySplashBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth= FirebaseAuth.getInstance()

        binding.ll.setOnClickListener{
            gotoLogin()
        }

        binding.signupTV.setOnClickListener{
            gotoSignup()
        }

        checkuser()

    }

    private fun checkuser() {

        var firebaseUser=firebaseAuth.currentUser
        if (firebaseUser!=null)
        {
            val ref= FirebaseDatabase.getInstance().getReference("Users")
            ref.child(firebaseUser.uid)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        //get User type
                        val userType=snapshot.child("userType").value
                        if (userType=="user")
                        {
                            //its ordinary user
                            startActivity(Intent(this@SplashActivity,CategoriesActivity::class.java))
                            finish()
                        }
                        else if (userType=="admin")
                        {
                            //its admin
                            startActivity(Intent(this@SplashActivity,CategoriesActivity::class.java))
                            finish()
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }
                })
        }

    }


    public fun gotoLogin()
    {
        startActivity(Intent(this,LoginActivity::class.java))
        finish()
    }
    fun gotoSignup()
    {
        startActivity(Intent(this,SignupActivity::class.java))
        finish()
    }




}