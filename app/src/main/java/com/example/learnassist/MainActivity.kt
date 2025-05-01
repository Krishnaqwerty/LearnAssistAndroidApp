package com.example.learnassist

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var greetingText: TextView
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        greetingText = findViewById(R.id.greetingText)
        auth = FirebaseAuth.getInstance()

        // Greet based on time (optional)
        var greeting = when (val hour = java.util.Calendar.getInstance().get(java.util.Calendar.HOUR_OF_DAY)) {
            in 5..11 -> "Good Morning 🌞"
            in 12..16 -> "Good Afternoon ☀️"
            in 17..21 -> "Good Evening 🌆"
            else -> "Welcome 🌙"
        }

        val tt = getString(R.string.welcome_message)

        greeting += tt

        greetingText.text = greeting


        // Delay 2 seconds then check user
        Handler(Looper.getMainLooper()).postDelayed({
            val currentUser = auth.currentUser
            if (currentUser != null) {
                // User is logged in → Go to Dashboard
                startActivity(Intent(this, DashboardActivity::class.java))
                finish()
            } else {
                // Not logged in → Go to Login/Register
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }, 2000) // 2-second delay like a splash screen
    }
}