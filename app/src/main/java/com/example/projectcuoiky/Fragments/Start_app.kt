package com.example.projectcuoiky.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.projectcuoiky.R

/**
 * A simple [Fragment] subclass.
 * Use the [start_app.newInstance] factory method to
 * create an instance of this fragment.
 */
class start_app : Fragment() {
    // TODO: Rename and change types of parameters

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_start_app, container, false)
    }
}