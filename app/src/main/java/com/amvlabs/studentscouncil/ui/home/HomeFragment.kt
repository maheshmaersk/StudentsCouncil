package com.amvlabs.studentscouncil.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.amvlabs.studentscouncil.RecycleUserAdapter
import com.amvlabs.studentscouncil.databinding.FragmentHomeBinding
import com.amvlabs.studentscouncil.models.UserDetail
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val TAG = HomeFragment::class.java.simpleName

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
        val homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        auth = Firebase.auth
        val currentUser = auth.currentUser
        val userDetailList = mutableListOf<UserDetail>()
        db.collection("report")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                    val data: MutableMap<String, Any> = document.data
                    currentUser?.uid?.let {
                        if (it.equals(data["userID"] as String?, true)) {
                            userDetailList.add(
                                UserDetail(
                                    category = data["category"] as String,
                                    userID = data["userID"] as String,
                                    description = data["description"] as String,
                                    email = data["email"] as String,
                                    report_description = data["report_description"] as String?,
                                    report_status = data["report_status"] as Long?,
                                )
                            )
                        }
                    }
                }
                loadRecyclerView(userDetailList)
                Log.d(TAG, "${"Complains Added"} => ${userDetailList.size}")
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }


        return root
    }

    fun loadRecyclerView(userDetailList: MutableList<UserDetail>) {
        binding.reportsList.layoutManager = LinearLayoutManager(context)
        binding.reportsList.adapter = RecycleUserAdapter(userDetailList)

        if (userDetailList.size > 0) {
            binding.reportsList.visibility = View.VISIBLE
            binding.noResultGroup.visibility = View.GONE
        } else {
            binding.noResultGroup.visibility = View.VISIBLE
            binding.reportsList.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}