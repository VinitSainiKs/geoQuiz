package com.example.geoquiz

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_main.*

private const val REQUEST_CODE_CHEAT = 0


class MainActivity : AppCompatActivity() {

    private val quizViewModel: QuizViewModel by lazy {
        ViewModelProviders.of(this).get(QuizViewModel::class.java)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button_true.setOnClickListener {
            checkAnswer(true)
            buttonAvailability(true)
        }

        button_false.setOnClickListener {
            checkAnswer(false)
            buttonAvailability(true)
        }

        ib_next.setOnClickListener {
            quizViewModel.moveToNext()
            updateQuestion()
            buttonAvailability(false)
        }

        ib_previous.setOnClickListener {
            if (quizViewModel.currentIndex == 0)
                quizViewModel.currentIndex = 5
            else
                quizViewModel.currentIndex = (quizViewModel.currentIndex-1)
            updateQuestion()
        }

        button_cheat.setOnClickListener {view ->
            val intent = Intent(this, CheatActivity::class.java)
            intent.putExtra("Answer", quizViewModel.currentQuestionAnswer)
            val option = ActivityOptions.makeClipRevealAnimation(view, 0, 0, view.width, view.height)
            startActivityForResult(intent, REQUEST_CODE_CHEAT, option.toBundle())
        }

        tv_question_text.setOnClickListener {
            if (quizViewModel.isCheater == true){
                Toast.makeText(this,"true", Toast.LENGTH_SHORT).show()
            }
            if (quizViewModel.isCheater == false){
                Toast.makeText(this, "false", Toast.LENGTH_SHORT).show()
            }
            updateQuestion()
        }
        updateQuestion()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != Activity.RESULT_OK){
            return
        }
        if (requestCode == REQUEST_CODE_CHEAT){
            quizViewModel.isCheater = data?.getBooleanExtra("EXTRA_ANSWER_SHOWN", false) ?: false
        }
    }


    fun buttonAvailability(flag: Boolean){
        if (flag) {
            button_true.isEnabled = false
            button_false.isEnabled = false
        }
        else{
            button_true.isEnabled = true
            button_false.isEnabled = true
        }
    }


    private fun updateQuestion(){

        val questionTextResId = quizViewModel.currentQuestionText
        tv_question_text.setText(questionTextResId)
    }

    private fun checkAnswer(userAnswer: Boolean){

        val correctAnswer = quizViewModel.currentQuestionAnswer
        when{
            quizViewModel.isCheater -> Toast.makeText(this, "Cheating is wrong", Toast.LENGTH_SHORT).show()
            userAnswer==correctAnswer -> Toast.makeText(this, "Correct Answer", Toast.LENGTH_SHORT).show()
            else -> Toast.makeText(this, "Wrong Answer", Toast.LENGTH_SHORT).show()
        }

    }

}
