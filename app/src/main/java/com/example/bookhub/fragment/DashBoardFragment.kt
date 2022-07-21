package com.example.bookhub.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bookhub.databinding.FragmentDashBoardBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.bookhub.R


class DashBoardFragment : Fragment(R.layout.fragment_dash_board) {

    lateinit var binding: FragmentDashBoardBinding

    lateinit var layoutManager: RecyclerView.LayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_dash_board, container, false)

        layoutManager = LinearLayoutManager(activity)

        return binding.recyclerDashboard
    }


}