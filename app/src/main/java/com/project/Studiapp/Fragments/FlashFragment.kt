package com.project.Studiapp.Fragments

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.project.Studiapp.Activities.HomeActivity
import com.project.Studiapp.Activities.LoginActivity
import com.project.Studiapp.databinding.FragmentFlashBinding

class FlashFragment : Fragment() {
    private lateinit var binding: FragmentFlashBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFlashBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.nextButton.setOnClickListener {
            val preferences = PreferenceManager.getDefaultSharedPreferences(activity)
            val GoogleLog = preferences.getString("login", "out")
            if (GoogleLog.equals("Login")) {
                val intent = Intent(activity, HomeActivity::class.java)
                startActivity(intent)
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                activity?.finish()
            } else {
                Toast.makeText(activity, "Please Login", Toast.LENGTH_SHORT).show()
                val intent = Intent(activity, LoginActivity::class.java)
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                startActivity(intent)
                activity?.finish()
            }
        }

        return view
    }


}