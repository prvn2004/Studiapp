package com.project.insurancesurveyorexam.Fragments

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.preference.PreferenceManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.project.insurancesurveyorexam.Activities.HomeActivity
import com.project.insurancesurveyorexam.Activities.LoginActivity
import com.project.insurancesurveyorexam.R
import com.project.insurancesurveyorexam.databinding.FragmentSplashBinding

class SplashFragment : Fragment() {
    private lateinit var binding: FragmentSplashBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSplashBinding.inflate(inflater, container, false)
        val view = binding.root

        Handler(Looper.getMainLooper()).postDelayed({
            showFragment(FlashFragment())
        }, 1000)

        return view
    }

    fun showFragment(fragment: Fragment) {
        val fram = activity?.supportFragmentManager?.beginTransaction()
        fram?.replace(R.id.splash_container, fragment)
        fram?.commit()
    }
}