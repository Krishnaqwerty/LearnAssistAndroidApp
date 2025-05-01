package com.example.learnassist

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.google.firebase.auth.FirebaseAuth


class QuizFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_quiz, container, false)

//
        val mathButton = view.findViewById<CardView>(R.id.box_math)
        mathButton.setOnClickListener {
            val intent = Intent(requireContext(), MathActivity::class.java)
            startActivity(intent)
        }
//
        val physicsButton = view.findViewById<CardView>(R.id.box_physics)
        physicsButton.setOnClickListener {
            val intent = Intent(requireContext(), MathActivity::class.java)
            startActivity(intent)
        }

        val biologyButton = view.findViewById<CardView>(R.id.box_biology)
        biologyButton.setOnClickListener {
            val intent = Intent(requireContext(), MathActivity::class.java)
            startActivity(intent)


        }

        val chemButton = view.findViewById<CardView>(R.id.box_chemistry)
        chemButton.setOnClickListener {
            val intent = Intent(requireContext(), MathActivity::class.java)
            startActivity(intent)


        }

        val mixButton = view.findViewById<CardView>(R.id.box_mix)
        mixButton.setOnClickListener {
            val intent = Intent(requireContext(), MathActivity::class.java)
            startActivity(intent)


        }







        return view
    }
}

