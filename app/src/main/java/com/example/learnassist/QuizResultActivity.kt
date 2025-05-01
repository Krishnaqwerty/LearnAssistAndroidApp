package com.example.learnassist

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RatingBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue

class QuizResultActivity : AppCompatActivity() {

    private lateinit var ratingBar: RatingBar
    private lateinit var scoreText: TextView
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_result)

        val score = intent.getIntExtra("score", 0)
        val total = intent.getIntExtra("total", 5)

        firebaseAuth = FirebaseAuth.getInstance()
        dbRef = FirebaseDatabase.getInstance().reference

        scoreText = findViewById(R.id.scoreText)
        ratingBar = findViewById(R.id.ratingBar)

        scoreText.text = "You scored $score / $total"
        ratingBar.rating = score.toFloat()

        saveToFirebase(score, total)

        val backBtn = findViewById<Button>(R.id.backToHomeBtn)
        backBtn.setOnClickListener {
            val intent = Intent(this, DashboardActivity::class.java) // or MainActivity
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }


    }

    private fun saveToFirebase(score: Int, total: Int) {
        val uid = firebaseAuth.currentUser?.uid ?: return
        val mathRef = dbRef.child("users").child(uid).child("quizResults").child("math")
        val data = mapOf(
            "score" to score,
            "total" to total,
            "timestamp" to ServerValue.TIMESTAMP
        )
        mathRef.setValue(data)
    }
}
