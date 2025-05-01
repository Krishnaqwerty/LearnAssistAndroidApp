package com.example.learnassist

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        val orb1 = findViewById<ImageView>(R.id.orb1)
        val orb2 = findViewById<ImageView>(R.id.orb2)
        val orb3 = findViewById<ImageView>(R.id.orb3)
        val orb4 = findViewById<ImageView>(R.id.orb4)
        val orb5 = findViewById<ImageView>(R.id.orb5)
        val orb6 = findViewById<ImageView>(R.id.orb6)
        val orb7 = findViewById<ImageView>(R.id.orb7)
        val orb8 = findViewById<ImageView>(R.id.orb8)
        val orb9 = findViewById<ImageView>(R.id.orb9)
        val orb11 = findViewById<ImageView>(R.id.orb11)
        val orb12 = findViewById<ImageView>(R.id.orb12)

        startOrbRotation(orb1)
        startOrbRotation(orb2)
        startOrbRotation(orb3)
        startOrbRotation(orb4)
        startOrbRotation(orb5)
        startOrbRotation(orb6)
        startOrbRotation(orb7)
        startOrbRotation(orb8)
        startOrbRotation(orb9)
        startOrbRotation(orb11)
        startOrbRotation(orb12)


        auth = FirebaseAuth.getInstance()

        val emailInput = findViewById<EditText>(R.id.emailLog)
        val passwordInput = findViewById<EditText>(R.id.passwordLog)
        val loginBtn = findViewById<Button>(R.id.loginBtn)

        val btnGoToRegister = findViewById<Button>(R.id.btnGoToRegister)

        btnGoToRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }


        loginBtn.setOnClickListener {
            val email = emailInput.text.toString()
            val password = passwordInput.text.toString()

            // Firebase Login
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                        val user = auth.currentUser
                        // Go to Dashboard
                        val intent = Intent(this, DashboardActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this, "Login Failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    private fun startOrbRotation(view: View) {
        val rotate = RotateAnimation(
            0f,
            360f,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        ).apply {
            duration = 8000
            repeatCount = Animation.INFINITE
            interpolator = LinearInterpolator()
        }

        view.startAnimation(rotate)
    }

}
