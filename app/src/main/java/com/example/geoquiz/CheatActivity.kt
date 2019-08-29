package com.example.geoquiz

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_cheat.*


class CheatActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheat)

        val answer = intent.getBooleanExtra("Answer", true)

        button_show_answer.setOnClickListener {
            tv_answer.setText("$answer")
            setAnswerShownResult(true)
        }

    }

    private fun setAnswerShownResult(isAnswerShown: Boolean) {

        val data = Intent().apply {
            putExtra("EXTRA_ANSWER_SHOWN", isAnswerShown)
        }
        setResult(Activity.RESULT_OK, data)

    }
}
