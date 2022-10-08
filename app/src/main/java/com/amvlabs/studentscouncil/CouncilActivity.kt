package com.amvlabs.studentscouncil

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.amvlabs.studentscouncil.databinding.ActivityCouncilBinding
import com.amvlabs.studentscouncil.databinding.ActivityHomeBinding
import com.amvlabs.studentscouncil.models.UserDetail
import com.amvlabs.studentscouncil.ui.home.HomeFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CouncilActivity : AppCompatActivity() {
    val db = Firebase.firestore
    private lateinit var auth: FirebaseAuth
    private val TAG = CouncilActivity::class.java.simpleName
    private lateinit var binding: ActivityCouncilBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCouncilBinding.inflate(layoutInflater)
        setContentView(binding.root)


        auth = Firebase.auth
        val userDetailList = mutableListOf<UserDetail>()
        db.collection("report")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                    val data: MutableMap<String, Any> = document.data
                    userDetailList.add(
                        UserDetail(
                            category = data["category"] as String,
                            userID = data["userID"] as String,
                            description = data["description"] as String,
                            email = data["email"] as String,
                            report_description = data["report_description"] as String?,
                            report_status = data["report_status"] as Long?,
                            documentID = document.id
                        )
                    )
                }
                loadRecyclerView(userDetailList)
                Log.d(TAG, "${"Complains Added"} => ${userDetailList.size}")
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.home_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menuLogout) {
            Firebase.auth.signOut()
            val mIntent = Intent(binding.root.context, LoginActivity::class.java)
            startActivity(mIntent)
            finishAffinity()
            return true
        }
        else {
            return true
        }
    }

    fun loadRecyclerView(userDetailList: MutableList<UserDetail>) {
        binding.complaintsList.layoutManager =
            LinearLayoutManager(baseContext, LinearLayoutManager.VERTICAL, false)
        binding.complaintsList.adapter = RecycleUserAdapter(userDetailList)

        if (userDetailList.size > 0) {
            binding.complaintsList.visibility = View.VISIBLE
            binding.noResultGroup.visibility = View.GONE
        } else {
            binding.noResultGroup.visibility = View.VISIBLE
            binding.complaintsList.visibility = View.GONE
        }
    }
}