package com.example.learnassist

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.Button
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MathActivity : AppCompatActivity() {

    private lateinit var questionText: TextView
    private lateinit var optionA: Button
    private lateinit var optionB: Button
    private lateinit var optionC: Button
    private lateinit var optionD: Button
    private lateinit var timerBar: ProgressBar
    private lateinit var questionCounter: TextView

    private var score = 0
    private var currentQuestionIndex = 0
    private val totalQuestions = 5
    private lateinit var questions: List<Question>

    private lateinit var countDownTimer: CountDownTimer

    data class Question(
        val question: String,
        val options: List<String>,
        val correctAnswer: String
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_math)

        // Init views
        questionText = findViewById(R.id.questionText)
        optionA = findViewById(R.id.optionA)
        optionB = findViewById(R.id.optionB)
        optionC = findViewById(R.id.optionC)
        optionD = findViewById(R.id.optionD)
        timerBar = findViewById(R.id.progressBarTimer)
        questionCounter = findViewById(R.id.questionCounter)

        // Load Questions
        questions = loadMathQuestions().shuffled().take(totalQuestions)

        loadQuestion()
    }

    private fun loadMathQuestions(): List<Question> {
        return listOf(
            Question("What is derivative of x²?", listOf("2x", "x", "x²", "None"), "2x"),
            Question("Limit of sinx/x as x→0?", listOf("0", "1", "∞", "Undefined"), "1"),
            Question("Integration of 1/x?", listOf("log x", "x", "1", "x²"), "log x"),
            Question("Area of circle formula?", listOf("πr²", "2πr", "πd", "None"), "πr²"),
            Question("What is 7 x 8?", listOf("56", "48", "64", "58"), "56")
        )
    }

    private fun loadQuestion() {
        if (currentQuestionIndex >= totalQuestions) {
            showResult()
            return
        }

        val q = questions[currentQuestionIndex]
        questionCounter.text = "Question ${currentQuestionIndex + 1}/$totalQuestions"
        questionText.text = q.question
        val options = q.options.shuffled()
        optionA.text = options[0]
        optionB.text = options[1]
        optionC.text = options[2]
        optionD.text = options[3]

        setOptionListener(optionA, q.correctAnswer)
        setOptionListener(optionB, q.correctAnswer)
        setOptionListener(optionC, q.correctAnswer)
        setOptionListener(optionD, q.correctAnswer)

        startTimer()
    }

    private fun setOptionListener(button: Button, correct: String) {
        button.setOnClickListener {
            countDownTimer.cancel()
            if (button.text == correct) {
                score++
            }
            currentQuestionIndex++
            loadQuestion()
        }
    }

    private fun startTimer() {
        timerBar.progress = 0
        countDownTimer = object : CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val progress = ((30 - millisUntilFinished / 1000)).toInt()
                timerBar.progress = progress
            }

            override fun onFinish() {
                currentQuestionIndex++
                loadQuestion()
            }
        }
        countDownTimer.start()
    }

    private fun showResult() {
        val intent = Intent(this, QuizResultActivity::class.java)
        intent.putExtra("score", score)
        intent.putExtra("total", totalQuestions)
        startActivity(intent)
        finish()
    }
}
