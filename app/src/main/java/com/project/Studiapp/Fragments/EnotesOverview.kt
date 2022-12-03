package com.project.Studiapp.Fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.project.Studiapp.databinding.FragmentEnotesOverviewBinding

class EnotesOverview : Fragment() {
private lateinit var binding: FragmentEnotesOverviewBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEnotesOverviewBinding.inflate(inflater, container, false)
        val view = binding.root

        val value = requireArguments().getString("YourKey")
        val content = requireArguments().getString("Content")

        val toolbartitle = value
        binding.ToolbarTitle.text = toolbartitle
        binding.textView12.text = content

        binding.threeLines.setOnClickListener {
           onBackPressed()
        }
        return view
    }


    fun onBackPressed() {
        val fm: FragmentManager? = fragmentManager
        if (fm?.getBackStackEntryCount()!! > 0) {
            Log.i("MainActivity", "popping backstack")
            fm.popBackStack()
        } else {
            Log.i("MainActivity", "nothing on backstack, calling super")
        }
    }
}