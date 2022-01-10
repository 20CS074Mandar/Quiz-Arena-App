package com.example.quiz

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.example.quiz.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class SignupActivity : AppCompatActivity() {

    //view binding
    private lateinit var binding:ActivitySignupBinding
    //Firebase Dialoge
    private lateinit var firebaseAuth:FirebaseAuth
    //Progress Dialoge
    private lateinit var progressDialog:ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //onclick Button takes u to loginActivity
        binding.loginTv.setOnClickListener{
            gotoLogin()
        }


        //initilazing firebase auth
        firebaseAuth= FirebaseAuth.getInstance()

        //progress dialog will be show while registering the user
        progressDialog=ProgressDialog(this)
        progressDialog.setTitle("Please Wait")
        progressDialog.setCanceledOnTouchOutside(false)


        binding.signupBtn.setOnClickListener{

            /*Steps
            * 1) Input Data
            * 2) Validate Data
            * 3) Create Account - Firebase auth
            * 4)Save user Info -Firebase Realtime Database */
            validateData()
        }
    }


    private var name=""
    private var email=""
    private var password=""

    private fun validateData() {

        name=binding.usernameEt.text.toString().trim()
        email=binding.emailEt.text.toString().trim()
        password=binding.passwrodEt.text.toString().trim()
        val cpassword=binding.cpasswordEt.text.toString().trim()


        if(name.isEmpty())
        {
            Toast.makeText(this,"Enter Your name...", Toast.LENGTH_SHORT).show()
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            Toast.makeText(this,"Invalid Email Pattern...", Toast.LENGTH_SHORT).show()
        }
        else if(password.isEmpty())
        {
            Toast.makeText(this,"Enter Password...", Toast.LENGTH_SHORT).show()
        }
        else if(cpassword.isEmpty())
        {
            Toast.makeText(this,"Confirm Password...", Toast.LENGTH_SHORT).show()
        }
        else if(password!=cpassword)
        {
            Toast.makeText(this,"Password Mismatch...", Toast.LENGTH_SHORT).show()
        }
        else
        {
            createUserAccount()
        }

    }

    private fun createUserAccount() {

        //3). Create user Account - Firease Auth
        progressDialog.setMessage("Creating Account....")
        progressDialog.show()

        //create user with firebase
        firebaseAuth.createUserWithEmailAndPassword(email,password)
            .addOnSuccessListener {
                //account created now add user info in DB
                updateUseInfo()
            }

            .addOnFailureListener{e->
                progressDialog.dismiss()
                Toast.makeText(this,"Failed creating account due to ${e.message}",Toast.LENGTH_SHORT).show()
            }
    }

    private fun updateUseInfo() {

        //4) Save user info -Firebase Realtime Database

        progressDialog.setMessage("Saving User info...")
        //timestamp
        val timestamp=System.currentTimeMillis()

        //get current user  uid, since  user is registered so we can get it now
        val uid=firebaseAuth.uid

        //setup data to add in db
        val hashMap:HashMap<String ,Any?> = HashMap()
        hashMap["uid"]=uid
        hashMap["email"]=email
        hashMap["name"]=name
        hashMap["profileImage"]=" "
        hashMap["userType"]="user"
        hashMap["timestamp"]=timestamp

        //set data to DB
        val ref= FirebaseDatabase.getInstance().getReference("Users")
        ref.child(uid!!)
            .setValue(hashMap)
            .addOnSuccessListener {
                //user info saved , open user dashboard
                progressDialog.dismiss()
                Toast.makeText(this,"Account Cerated...",Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@SignupActivity,LoginActivity::class.java))
            }
            .addOnFailureListener{e->
                //failed adding data to DB
                progressDialog.dismiss()
                Toast.makeText(this,"Failed saving user info due to ${e.message}",Toast.LENGTH_SHORT).show()
            }

    }

    //takes user to login Page
    private fun gotoLogin()
    {
        startActivity(Intent(this,LoginActivity::class.java))
        finish()
    }

}