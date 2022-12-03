package com.project.Studiapp.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.project.Studiapp.R
import com.project.Studiapp.databinding.FragmentLiveTestMainBinding

class LiveTestMainFragment : Fragment() {
    private lateinit var binding: FragmentLiveTestMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding  = FragmentLiveTestMainBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.threeLines.setOnClickListener {
            activity?.finish()
        }

        showFragment(UpcomingTestFragment())
        binding.upcomingButton.setBackgroundResource(R.drawable.underline_background)
        binding.upcomingButton.setOnClickListener {
            showFragment(UpcomingTestFragment())
            binding.upcomingButton.setBackgroundResource(R.drawable.underline_background)
            binding.pastButton.setBackgroundResource(R.color.white)

        }
        binding.pastButton.setOnClickListener {
            showFragment(PastTestFragment())
            binding.pastButton.setBackgroundResource(R.drawable.underline_background)
            binding.upcomingButton.setBackgroundResource(R.color.white)
        }

        return view
    }
    fun showFragment(fragment: Fragment) {
        val fram = activity?.supportFragmentManager?.beginTransaction()
        fram?.replace(R.id.container_live_test, fragment)
        fram?.commit()
    }
}