package com.example.learnassist

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RatingBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class ProgressFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var dbRef: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_progress, container, false)

        auth = FirebaseAuth.getInstance()
        dbRef = FirebaseDatabase.getInstance().getReference("users")

        val uid = auth.currentUser?.uid

//        val testing = view.findViewById<CardView>(R.id.card_math)
//
//        testing.findViewById<TextView>(R.id.subjectTitle).text = snapshot.child("Math").child("score").toString()


//        val ss = dbRef.child(uid!!).get()
//        val score = ss.result.child("Math").child("score").value.toString()


        uid?.let {
            dbRef.child(uid).child("quizResults").get().addOnSuccessListener { snapshot ->
                if (snapshot.exists()) {



                    updateProgressCard(view.findViewById<CardView>(R.id.card_math), snapshot, "math")
                    updateProgressCard(view.findViewById<CardView>(R.id.card_physics1), snapshot, "Physics")
                    updateProgressCard(view.findViewById<CardView>(R.id.card_chemistry1), snapshot, "Chemistry")
                    updateProgressCard(view.findViewById<CardView>(R.id.card_biology1), snapshot, "Biology")
                }else {



                    // Handle the case where the user's progress data doesn't exist
                    val defaultMessage = "No data yet"
                    val defaultLevel = "Level: N/A"

                    val subjects = listOf(
                        Triple(R.id.card_math, "Math", R.drawable.ic_iron_medal),
                        Triple(R.id.card_math, "Physics", R.drawable.ic_iron_medal),
                        Triple(R.id.card_math, "Chemistry", R.drawable.ic_iron_medal),
                        Triple(R.id.card_math, "Biology", R.drawable.ic_iron_medal)
                    )

                    for ((cardId, subjectName, medalRes) in subjects) {
                        val card = view.findViewById<View>(cardId)

                        card.findViewById<TextView>(R.id.subjectTitle).text = subjectName
                        card.findViewById<ProgressBar>(R.id.progressBar).progress = 0
                        card.findViewById<TextView>(R.id.levelText).text = defaultLevel
                        card.findViewById<RatingBar>(R.id.starRating).rating = 0f
                        card.findViewById<ImageView>(R.id.medalImage).setImageResource(medalRes)
                    }
                }

            }

        }

        return view
    }

    private fun updateProgressCard(
        cardView: View, snapshot: DataSnapshot, subject: String
    ) {
        val score = snapshot.child(subject).child("score").getValue(Int::class.java) ?: 0
        val total = snapshot.child(subject).child("total").getValue(Int::class.java) ?: 0

        Log.d("ProgressFragment", "Score for $subject: $score")
        Log.d("ProgressFragment", "Total for $subject: $total")

//        val subjectText = cardView.findViewById<TextView>(R.id.subjectTitle)
        val progressBar = cardView.findViewById<ProgressBar>(R.id.progressBar)
        val levelText = cardView.findViewById<TextView>(R.id.levelText)
        val stars = cardView.findViewById<RatingBar>(R.id.starRating)
        val medalImage = cardView.findViewById<ImageView>(R.id.medalImage)

        val percentage = if (total != 0) {
            (score * 100) / total
        } else {
            0  // or any fallback value you want to use
        }

//        subjectText.text = subject
        progressBar.progress = percentage

        when (percentage) {
            in 0..20 -> {
                levelText.text = "Level: Beginner"
                stars.rating = 1f
                medalImage.setImageResource(R.drawable.ic_iron_medal)
            }
            in 21..40 -> {
                levelText.text = "Level: Learner"
                stars.rating = 2f
                medalImage.setImageResource(R.drawable.ic_bronze_medal)
            }
            in 41..60 -> {
                levelText.text = "Level: Intermediate"
                stars.rating = 3f
                medalImage.setImageResource(R.drawable.ic_silver_medal)
            }
            in 61..80 -> {
                levelText.text = "Level: Advanced"
                stars.rating = 4f
                medalImage.setImageResource(R.drawable.ic_gold_medal)
            }
            else -> {
                levelText.text = "Level: Expert"
                stars.rating = 5f
                medalImage.setImageResource(R.drawable.ic_diamond_medal)
            }
        }
    }

}
