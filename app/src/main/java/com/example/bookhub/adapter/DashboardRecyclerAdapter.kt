package com.example.bookhub.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bookhub.R
import java.util.*

class DashboardRecyclerAdapter (val context: Context, val itemList: ArrayList<String>) : RecyclerView.Adapter<DashboardRecyclerAdapter.DashBoardViewHolder>() {

    class DashBoardViewHolder (view: View): RecyclerView.ViewHolder(view){

        val textView: TextView = view.findViewById(R.id.txtBookName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashBoardViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_dashboard_single_row, parent, false)

        return DashBoardViewHolder(view)
    }

    override fun onBindViewHolder(holder: DashBoardViewHolder, position: Int) {

        holder.textView.text = itemList[position]
    }

    override fun getItemCount(): Int {

        return itemList.size
    }
}