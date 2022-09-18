package com.amvlabs.studentscouncil

import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.amvlabs.studentscouncil.databinding.ActivityMainBinding
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAnalytics = Firebase.analytics
        binding.btStudent.setOnClickListener {

            firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_ITEM) {
                param(FirebaseAnalytics.Param.ITEM_ID, "LoginScreen")
                param(FirebaseAnalytics.Param.ITEM_NAME, "Student")
                param(FirebaseAnalytics.Param.CONTENT_TYPE, "button")
            }
            binding.loginLayout.setBackgroundResource(R.drawable.day)
        }
        binding.btCouncil.setOnClickListener {
            binding.loginLayout.setBackgroundResource(R.drawable.night)
            firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_ITEM) {
                param(FirebaseAnalytics.Param.ITEM_ID, "LoginScreen")
                param(FirebaseAnalytics.Param.ITEM_NAME, "Council")
                param(FirebaseAnalytics.Param.CONTENT_TYPE, "button")
            }
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