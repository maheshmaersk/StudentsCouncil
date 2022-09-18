package com.amvlabs.studentscouncil

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.amvlabs.studentscouncil.databinding.ActivityMainBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btStudent.setOnClickListener {
            binding.loginLayout.setBackgroundResource(R.drawable.day)
        }
        binding.btCouncil.setOnClickListener {
            binding.loginLayout.setBackgroundResource(R.drawable.night)
        }

        binding.textView6.setOnClickListener {
            val mIntent = Intent(binding.root.context, SignUpActivity::class.java)
            startActivity(mIntent)
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