package com.amvlabs.studentscouncil

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.amvlabs.studentscouncil.databinding.ActivityLoginBinding
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    private lateinit var firebaseAnalytics: FirebaseAnalytics
    private lateinit var auth: FirebaseAuth
    private var isStudentSelected: Boolean = true
    private val TAG = LoginActivity::class.java.simpleName


    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            navigateToHome()
        }
    }


    private fun navigateToHome() {
        val mIntent = Intent(binding.root.context, HomeActivity::class.java)
        startActivity(mIntent)
        finish()
    }

    private fun navigateToCouncilHome() {
        val mIntent = Intent(binding.root.context, CouncilActivity::class.java)
        startActivity(mIntent)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        firebaseAnalytics = Firebase.analytics
        binding.btStudent.setOnClickListener {
            firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_ITEM) {
                param(FirebaseAnalytics.Param.ITEM_ID, "LoginScreen")
                param(FirebaseAnalytics.Param.ITEM_NAME, "Student")
                param(FirebaseAnalytics.Param.CONTENT_TYPE, "button")
            }
            binding.loginLayout.setBackgroundResource(R.drawable.day)
            isStudentSelected = true
        }
        binding.btCouncil.setOnClickListener {
            binding.loginLayout.setBackgroundResource(R.drawable.night)
            firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_ITEM) {
                param(FirebaseAnalytics.Param.ITEM_ID, "LoginScreen")
                param(FirebaseAnalytics.Param.ITEM_NAME, "Council")
                param(FirebaseAnalytics.Param.CONTENT_TYPE, "button")
            }
            isStudentSelected = false
        }

        binding.textView6.setOnClickListener {
            firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_ITEM) {
                param(FirebaseAnalytics.Param.ITEM_ID, "LoginScreen")
                param(FirebaseAnalytics.Param.ITEM_NAME, "SignUpClicked")
                param(FirebaseAnalytics.Param.CONTENT_TYPE, "button")
            }
            val mIntent = Intent(binding.root.context, SignUpActivity::class.java)
            startActivity(mIntent)
        }

        binding.btLogin.setOnClickListener {

            val email = binding.inputUsername.text?.trim().toString()
            val password = binding.inputPassword.text?.trim().toString()

            Log.e("Username ", "${email} ${password}")

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success")
                        val user = auth.currentUser
                        user?.let {
                            if ((it.uid.equals(
                                    "b1AmFNoJ2GNQvcZi08MDeFlsfaF2",
                                    true
                                ) || it.uid.equals(
                                    "c5rD4Xi8kfcuCMt9xHae057Evoq2",
                                    true
                                ) || it.uid.equals(
                                    "7hBznOlvM0WzrnVGapUUWBZjo9I2",
                                    true
                                ))
                            ) {
                                if (isStudentSelected) {
                                    Toast.makeText(
                                        baseContext, "Oops Looks like student is selected",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else {
                                    navigateToCouncilHome()
                                }

                            } else {
                                navigateToHome()
                            }
                        }
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }


            firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_ITEM) {
                param(FirebaseAnalytics.Param.ITEM_ID, "LoginScreen")
                param(FirebaseAnalytics.Param.ITEM_NAME, "LoginClicked")
                param(FirebaseAnalytics.Param.CONTENT_TYPE, "button")
            }
        }

//
//        binding.student.setOnClickListener {
//
//            binding.councilGroup.visibility = View.GONE
//            binding.studentGroup.visibility = View.VISIBLE
//        }
//        binding.council.setOnClickListener {
//
//            binding.councilGroup.visibility = View.VISIBLE
//            binding.studentGroup.visibility = View.GONE
//        }

    }
}