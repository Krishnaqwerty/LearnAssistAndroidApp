package com.example.learnassist

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class HomeFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var dbRef: DatabaseReference
    private lateinit var recommendationsLayout: LinearLayout
    private val videoMap = mapOf(
        "Math" to "ZBvGUb0l7U8",
        "Physics" to "kKKM8Y-u7ds",
        "Chemistry" to "qxyi2Vo5r7Q",
        "Biology" to "BxbmFNyUb4E"
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        auth = FirebaseAuth.getInstance()
        dbRef = FirebaseDatabase.getInstance().getReference("users")

        val greetingText = view.findViewById<TextView>(R.id.greetingText)
        val email = auth.currentUser?.email
        greetingText.text = "Hi, $email 👋"

        Log.d("HomeFragment", "Greeting text set to: $email")

        recommendationsLayout = view.findViewById(R.id.recommendationsLayout)

        auth.currentUser?.uid?.let { uid ->
            dbRef.child(uid).child("quizResults").get().addOnSuccessListener { snapshot ->
                if (snapshot.exists()) {
                    for (subject in videoMap.keys) {
                        val score = snapshot.child(subject).child("score").getValue(Int::class.java) ?: 0
                        val total = snapshot.child(subject).child("total").getValue(Int::class.java) ?: 1
                        val percentage = if (total != 0) (score * 100 / total) else 0

                        if (percentage < 80) { // Show recommendation if performance needs improvement
                            val videoId = videoMap[subject]
                            if (videoId != null) {
                                addRecommendationCard(subject, videoId)
                            }
                        }
                    }
                }
            }
        }

        return view
    }

    private fun addRecommendationCard(subject: String, videoId: String) {
        val cardView = layoutInflater.inflate(R.layout.recommendation_card, null)

        val thumbnail = cardView.findViewById<ImageView>(R.id.videoThumbnail)
        val subjectName = cardView.findViewById<TextView>(R.id.subjectName)

        subjectName.text = subject

        val thumbnailUrl = "https://img.youtube.com/vi/$videoId/0.jpg"
        Glide.with(requireContext()).load(thumbnailUrl).into(thumbnail)

        cardView.setOnClickListener {
            val intent = Intent(requireContext(), VideoPlayerActivity::class.java)
            intent.putExtra("videoId", videoId)
            startActivity(intent)
        }

        recommendationsLayout.addView(cardView)
    }
}
