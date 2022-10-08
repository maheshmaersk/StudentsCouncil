package com.amvlabs.studentscouncil

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.amvlabs.studentscouncil.databinding.ActivityCouncilBinding
import com.amvlabs.studentscouncil.databinding.ActivityReportResultBinding
import com.amvlabs.studentscouncil.models.UserDetail
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ReportResultActivity : AppCompatActivity() {

    private val TAG = CouncilActivity::class.java.simpleName
    private lateinit var binding: ActivityReportResultBinding
    private lateinit var mUserDetail: UserDetail
    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityReportResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userDetail = intent.getSerializableExtra("userList")
        if (userDetail != null) {
            mUserDetail = userDetail as UserDetail
        }

        binding.tetEmail.setText(mUserDetail.email)
        binding.tetCategory.setText(mUserDetail.category)
        binding.tetDescription.setText(mUserDetail.description)
        binding.tetSolution.setText(mUserDetail.report_description)
        binding.spSolution.setSelection(mUserDetail.report_status!!.toInt())

        binding.tetEmail.keyListener = null
        binding.tetCategory.keyListener = null
        binding.tetDescription.keyListener = null


        binding.submitSolution.setOnClickListener {
            val solution = binding.tetSolution.text.toString().trim()
            val solutionState = binding.spSolution.selectedItemPosition

            if (solution.isEmpty()) {
                Toast.makeText(
                    it.context, "Please Enter the Solution.",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (solutionState <= 0) {
                Toast.makeText(
                    it.context, "Please select solution state.",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                mUserDetail.documentID?.let { documentID ->
                    db.collection("report").document(documentID).update(
                        mapOf(
                            "report_description" to solution,
                            "report_status" to solutionState
                        )
                    )
                    Toast.makeText(
                        it.context, "Successfully Updated.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                finish()
            }

        }
    }
}