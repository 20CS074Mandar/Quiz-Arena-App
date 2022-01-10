package com.example.quiz

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.example.quiz.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    //VIEW BINDING
    private lateinit var binding: ActivityLoginBinding
    //FIREBASE AUTH
    private lateinit var firebaseAuth: FirebaseAuth
    //PRGRESS DIALOG
    private lateinit var progressDialog:ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //init firebase auth
        firebaseAuth= FirebaseAuth.getInstance()

        //progress dialog will be show while login the user
        progressDialog=ProgressDialog(this)
        progressDialog.setTitle("Please Wait")
        progressDialog.setCanceledOnTouchOutside(false)

        binding.signupTv.setOnClickListener {
            gotoSignup()
        }

        binding.loginBtn.setOnClickListener{
            /*Steps
                * 1) Input Data
                * 2) Validate Data
                * 3) Login - Firebase Auth
                * 4) Check user type - Firebase auth
                *       if user - Move to user Dashboard
                *       if admin- Move to admin Dashboard
                */
            validateData()

        }

    }


    fun gotoSignup() {
        startActivity(Intent(this, SignupActivity::class.java))
    }

    private var email=""
    private var password=""


    private fun validateData() {

        //1) InputData()
        email=binding.emailEt.text.toString().trim()
        password=binding.passwrodEt.text.toString().trim()

        //2)Validate data
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            Toast.makeText(this,"Invalid Email Pattern...", Toast.LENGTH_SHORT).show()
        }
        else if(password.isEmpty())
        {
            Toast.makeText(this,"Enter Password...", Toast.LENGTH_SHORT).show()
        }
        else{
            loginUser()
        }
    }

    private fun loginUser() {
        //3) LOgin-Firebase auth

        //show progress
        progressDialog.setMessage("Logging in...")
        progressDialog.show()

        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                //Login successfull
                startActivity(Intent(this,CategoriesActivity::class.java))
                finish()
            }
            .addOnFailureListener{e->
                progressDialog.dismiss()
                Toast.makeText(this,"Login Failed due to ${e.message}",Toast.LENGTH_SHORT).show()
            }
    }


}