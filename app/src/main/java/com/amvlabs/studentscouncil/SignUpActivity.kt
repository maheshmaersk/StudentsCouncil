package com.amvlabs.studentscouncil

import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.amvlabs.studentscouncil.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var auth: FirebaseAuth
    private val TAG = SignUpActivity::class.java.simpleName

    override fun onStart() {
        super.onStart()

        val currentUser = auth.currentUser
        if (currentUser != null) {
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth


        binding.btSignup.setOnClickListener {
            auth.createUserWithEmailAndPassword(
                binding.tetEmail.text.toString(),
                binding.tetPassword.text.toString()
            ).addOnSuccessListener {
                Log.d(TAG, "registration: ${it.user}")
                Toast.makeText(
                    baseContext, "Registration Success.",
                    Toast.LENGTH_SHORT
                ).show()
            }.addOnFailureListener { t ->
                Toast.makeText(
                    baseContext, "Registration failed.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    }
}