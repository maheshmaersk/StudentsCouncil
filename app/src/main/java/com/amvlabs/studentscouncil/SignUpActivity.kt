package com.amvlabs.studentscouncil

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import com.amvlabs.studentscouncil.databinding.ActivityMainBinding
import com.amvlabs.studentscouncil.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

         binding.btSignup.setOnClickListener {
         }
    }
}