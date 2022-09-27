package com.amvlabs.studentscouncil

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.appcompat.widget.AppCompatTextView
import com.amvlabs.studentscouncil.models.UserDetail


class ComplainsAdapter(
    private val context: Context,
    private val complainList: ArrayList<UserDetail>
) :
    BaseAdapter() {

    private val inflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return complainList.size
    }

    override fun getItem(position: Int): Any {
        return complainList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val mComplaintDetail = getItem(position) as UserDetail
        val view: View
        val holder: ViewHolder

        if (convertView == null) {
            view = inflater.inflate(R.layout.list_item_complain, parent, false)
            holder = ViewHolder()

            holder.title = view.findViewById<AppCompatTextView>(R.id.complainTitle)
            holder.desc = view.findViewById<AppCompatTextView>(R.id.complainDescription)
            holder.price = view.findViewById<AppCompatTextView>(R.id.complainStatus)

            view.tag = holder
        } else {
            view = convertView
            holder = convertView.tag as ViewHolder
        }

        val titleTextView = holder.title
        val subtitleTextView = holder.desc
        val detailTextView = holder.price

        titleTextView.text = mComplaintDetail.category
        subtitleTextView.text = mComplaintDetail.description
        detailTextView.text = mComplaintDetail.report_status.toString()

        return view
    }


    private class ViewHolder {
        lateinit var title: AppCompatTextView
        lateinit var desc: AppCompatTextView
        lateinit var price: AppCompatTextView
    }


}