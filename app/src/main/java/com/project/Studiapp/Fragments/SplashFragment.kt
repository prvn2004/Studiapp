package com.project.Studiapp.Fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.project.Studiapp.R
import com.project.Studiapp.databinding.FragmentSplashBinding

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