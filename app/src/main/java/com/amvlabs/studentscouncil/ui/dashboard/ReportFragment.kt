package com.amvlabs.studentscouncil.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.amvlabs.studentscouncil.LoginActivity
import com.amvlabs.studentscouncil.databinding.FragmentReportBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ReportFragment : Fragment() {

    private var _binding: FragmentReportBinding? = null
    private val TAG = ReportFragment::class.java.simpleName

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    val db = Firebase.firestore
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel = ViewModelProvider(this)[ReportViewModel::class.java]
        _binding = FragmentReportBinding.inflate(inflater, container, false)
        val root: View = binding.root
        auth = Firebase.auth
        val currentUser = auth.currentUser
//        val textView: TextView = binding.textDashboard
        dashboardViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
        }

        binding.progressBar.visibility = View.GONE

        binding.reportIssue.setOnClickListener {
            val user = hashMapOf(
                "category" to binding.spCategory.selectedItem,
                "description" to binding.tetDescription.text.toString().trim(),
                "email" to binding.tetEmail.text.toString().trim(),
                "userID" to currentUser?.uid,
                "report_description" to "",
                "report_status" to 0
            )
            binding.progressBar.visibility = View.VISIBLE
            db.collection("report").add(user)
                .addOnSuccessListener { documentReference ->
                    binding.spCategory.setSelection(0)
                    binding.tetDescription.text=null
                    binding.tetEmail.text=null
                    Toast.makeText(
                        it.context, "Report has been submitted successfully.",
                        Toast.LENGTH_SHORT
                    ).show()
                    binding.progressBar.visibility = View.GONE
                    Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                }
                .addOnFailureListener { e ->
                    Toast.makeText(
                        it.context, "There seems to be an issue we will rectify soon. Please try after sometime",
                        Toast.LENGTH_SHORT
                    ).show()
                    binding.progressBar.visibility = View.GONE
                    Log.w(TAG, "Error adding document", e)
                }
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}