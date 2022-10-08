package com.amvlabs.studentscouncil

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.amvlabs.studentscouncil.models.UserDetail

class ComplainsAdapter(private var userList: MutableList<UserDetail>) :
    RecyclerView.Adapter<ComplainsAdapter.UserViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val vie =
            LayoutInflater.from(parent.context).inflate(R.layout.user_info_adapter, parent, false)
        return UserViewHolder(vie)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.title.text = userList[position].email
        holder.description.text = userList[position].description


        userList[position].report_status?.let { position ->
            if (position.toInt() == 1) {
                holder.card.setCardBackgroundColor(
                    ContextCompat.getColor(
                        holder.title.context,
                        R.color.orange
                    )
                )
                holder.status.text = "In Progress"
            } else if (position.toInt() == 2) {
                holder.card.setCardBackgroundColor(
                    ContextCompat.getColor(
                        holder.title.context,
                        R.color.green
                    )
                )
                holder.status.text = "Solved"
            } else if (position.toInt() == 3) {
                holder.card.setCardBackgroundColor(
                    ContextCompat.getColor(
                        holder.title.context,
                        R.color.red
                    )
                )
                holder.status.text = "Rejected"
            }
        }
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
        var card = view.findViewById<CardView>(R.id.ccReport)
        var status = view.findViewById<AppCompatTextView>(R.id.description)
        var description = view.findViewById<AppCompatTextView>(R.id.status)
    }
}