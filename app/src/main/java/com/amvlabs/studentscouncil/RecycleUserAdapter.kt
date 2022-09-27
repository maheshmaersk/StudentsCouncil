package com.amvlabs.studentscouncil

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.amvlabs.studentscouncil.models.UserDetail

class RecycleUserAdapter(private var userList: MutableList<UserDetail>) :
    RecyclerView.Adapter<RecycleUserAdapter.UserViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val vie = LayoutInflater.from(parent.context).inflate(R.layout.user_info_adapter,parent,false)
        return UserViewHolder(vie)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.title.text = userList[position].email
        holder.status.text =userList[position].report_status.toString()
        holder.description.text = userList[position].description
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var title = view.findViewById<AppCompatTextView>(R.id.title)
        var status = view.findViewById<AppCompatTextView>(R.id.description)
        var description = view.findViewById<AppCompatTextView>(R.id.status)
    }
}