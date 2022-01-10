package com.example.quiz

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import com.example.quiz.databinding.ActivityQuestionsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class QuestionsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuestionsBinding
    private lateinit var database: FirebaseDatabase


    var categoryName=""
    var quizQuestion:String = ""
    var quizAnswerA:String=""
    var quizAnswerB:String=""
    var quizAnswerC:String=""
    var quizAnswerD:String=""
    var quizCorrectAnswer:String=""
    var questionCount:Int=0
    var questionNumber:Int=1
    var userAnswer:String=""
    var userScore:Int=0


    private lateinit var auth: FirebaseAuth
    private lateinit var user: FirebaseUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuestionsBinding.inflate(layoutInflater)
        val view = binding.root
        database = FirebaseDatabase.getInstance()
        setContentView(view)

        auth = FirebaseAuth.getInstance()
        user = auth.currentUser!!

        val i=intent
        categoryName=i.getStringExtra("categoryname").toString()
        binding.catecoyName.text=categoryName



        withFirebase()
        binding.btnNext.setOnClickListener {
            withFirebase()
        }

        binding.optionA.setOnClickListener {
            userAnswer = "a"
            if (quizCorrectAnswer.equals(userAnswer)) {
                binding.optionA.setBackgroundResource(R.drawable.correct_answer)
                userScore++
                //binding.tvScore.text = userScore.toString()
            } else {
                binding.optionA.setBackgroundResource(R.drawable.wrong_answer)
                findAnswer()
            }
        }
        binding.optionB.setOnClickListener {
            userAnswer = "b"
            if (quizCorrectAnswer.equals(userAnswer)) {
                binding.optionB.setBackgroundResource(R.drawable.correct_answer)
                userScore++
                //binding.tvScore.text = userScore.toString()

            } else {
                binding.optionB.setBackgroundResource(R.drawable.wrong_answer)
                findAnswer()
            }
        }
        binding.optionC.setOnClickListener {
            userAnswer = "c"
            if (quizCorrectAnswer.equals(userAnswer)) {
                binding.optionC.setBackgroundResource(R.drawable.correct_answer)
                userScore++
                //binding.tvScore.text = userScore.toString()
            } else {
                binding.optionC.setBackgroundResource(R.drawable.wrong_answer)
                findAnswer()
            }
        }
        binding.optionD.setOnClickListener {
            userAnswer = "d"
            if (quizCorrectAnswer.equals(userAnswer)) {
                binding.optionD.setBackgroundResource(R.drawable.correct_answer)
                userScore++
                //binding.tvScore.text = userScore.toString()
            } else {
                binding.optionD.setBackgroundResource(R.drawable.wrong_answer)
                findAnswer()
            }
        }




    }

    public fun findAnswer() {

        if (quizCorrectAnswer.equals("a")) {
            binding.optionA.setBackgroundResource(R.drawable.correct_answer)
            binding.optionA.isEnabled=true
        } else if (quizCorrectAnswer.equals("b")) {
            binding.optionB.setBackgroundResource(R.drawable.correct_answer)
            binding.optionA.isEnabled=true
        } else if (quizCorrectAnswer.equals("c")) {
            binding.optionC.setBackgroundResource(R.drawable.correct_answer)
            binding.optionC.isEnabled=true
        }else if (quizCorrectAnswer.equals("d")) {
            binding.optionD.setBackgroundResource(R.drawable.correct_answer)
        }

    }


    public fun sendScore_to_firebase() {

        var userUID: String = user.uid
        val ref = FirebaseDatabase.getInstance().getReference("Users")
        ref.child(userUID).child("User Score").setValue(userScore)
            .addOnSuccessListener {
                Toast.makeText(this, "Score Updated Successfully", Toast.LENGTH_SHORT).show()

            }
    }


    public fun withFirebase()
    {

        val categoryRef=FirebaseDatabase.getInstance().getReference("Categories")
            .child("${categoryName}").child("Questions")
        categoryRef.addValueEventListener(object:ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {

                binding.optionA.setBackgroundResource(R.drawable.options)
                binding.optionB.setBackgroundResource(R.drawable.options)
                binding.optionC.setBackgroundResource(R.drawable.options)
                binding.optionD.setBackgroundResource(R.drawable.options)

                questionCount = snapshot.childrenCount.toInt()


                quizQuestion =
                    snapshot.child(questionNumber.toString()).child("q").getValue().toString()
                quizAnswerA =
                    snapshot.child(questionNumber.toString()).child("a").getValue().toString()
                quizAnswerB =
                    snapshot.child(questionNumber.toString()).child("b").getValue().toString()
                quizAnswerC =
                    snapshot.child(questionNumber.toString()).child("c").getValue().toString()
                quizAnswerD =
                    snapshot.child(questionNumber.toString()).child("d").getValue().toString()
                quizCorrectAnswer =
                    snapshot.child(questionNumber.toString()).child("answer").getValue().toString()

                binding.questionTv.text = quizQuestion
                binding.optionA.setText(quizAnswerA)
                binding.optionB.setText(quizAnswerB)
                binding.optionC.setText(quizAnswerC)
                binding.optionD.setText(quizAnswerD)
                binding.progress.text="${questionNumber}"+"/"+"${questionCount}"


                if (questionNumber <= questionCount) {
                    questionNumber++;
                } else {
                    Toast.makeText(
                        applicationContext,
                        "You Answerred all the questions",
                        Toast.LENGTH_SHORT
                    ).show()

                    startActivity(Intent(this@QuestionsActivity, ResultActivity::class.java))
                    sendScore_to_firebase()
                }

            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }



}
