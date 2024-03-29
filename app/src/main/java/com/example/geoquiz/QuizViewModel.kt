package com.example.geoquiz

import androidx.lifecycle.ViewModel

class QuizViewModel: ViewModel() {

    val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia,true)
    )

    var currentIndex = 0
    var isCheater = false
    var cheatNo = 0

    val currentQuestionAnswer: Boolean get() = questionBank[currentIndex].answer
    val currentQuestionText: Int get() = questionBank[currentIndex].textResId

    fun increaseCheatNo(){
        cheatNo = (cheatNo + 1)
    }

    fun moveToNext() {
        currentIndex = (currentIndex + 1) % questionBank.size
    }
}