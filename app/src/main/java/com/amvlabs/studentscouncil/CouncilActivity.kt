package com.amvlabs.studentscouncil

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amvlabs.studentscouncil.databinding.ActivityCouncilBinding
import com.amvlabs.studentscouncil.databinding.ActivityHomeBinding
import com.amvlabs.studentscouncil.models.UserDetail
import com.amvlabs.studentscouncil.ui.home.HomeFragment
import com.google.android.material.snackbar.Snackbar
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
        binding.progressBar.visibility = View.VISIBLE
        db.collection("report")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                    val data: MutableMap<String, Any> = document.data
                    if (!(data["isDeleted"] as Boolean)) {
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
        } else {
            return true
        }
    }

    private fun loadRecyclerView(userDetailList: MutableList<UserDetail>) {
        binding.complaintsList.layoutManager =
            LinearLayoutManager(baseContext, LinearLayoutManager.VERTICAL, false)
        binding.complaintsList.adapter = RecycleUserAdapter(userDetailList)
        val recycleAdapter = RecycleUserAdapter(userDetailList)
        binding.progressBar.visibility = View.GONE

        if (userDetailList.size > 0) {
            binding.complaintsList.visibility = View.VISIBLE
            binding.noResultGroup.visibility = View.GONE
        } else {
            binding.noResultGroup.visibility = View.VISIBLE
            binding.complaintsList.visibility = View.GONE
        }

        recycleAdapter.notifyDataSetChanged()

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val userDetail: UserDetail = userDetailList[viewHolder.adapterPosition]

                val position = viewHolder.adapterPosition

                userDetailList.removeAt(viewHolder.adapterPosition)

                recycleAdapter.notifyItemRemoved(viewHolder.adapterPosition)

                userDetail.documentID?.let { documentID ->
                    db.collection("report").document(documentID).update(
                        mapOf(
                            "isDeleted" to true,
                        )
                    )
                    Toast.makeText(
                        binding.complaintsList.context, "Successfully Deleted.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                Snackbar.make(
                    binding.complaintsList,
                    "Deleted " + userDetail.email,
                    Snackbar.LENGTH_LONG
                ).show()
//                    .setAction(
//                        "Undo",
//                        View.OnClickListener {
//                            userDetailList.add(position, userDetail)
//                            recycleAdapter.notifyItemInserted(position)
//                        }).show()
            }
        }).attachToRecyclerView(binding.complaintsList)

    }
}