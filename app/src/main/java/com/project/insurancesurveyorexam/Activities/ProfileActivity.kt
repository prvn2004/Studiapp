package com.project.insurancesurveyorexam.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.project.insurancesurveyorexam.Fragments.ProfileFragment
import com.project.insurancesurveyorexam.R
import com.project.insurancesurveyorexam.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProfileBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val name = intent.getStringExtra("Name")
        val email  = intent.getStringExtra("Email")

        showFragment(ProfileFragment(), name, email)
    }

    fun showFragment(fragment: Fragment, name: String?, email: String?) {
        val fram = supportFragmentManager.beginTransaction()
        val frag: Fragment = fragment
        val args = Bundle()
        args.putString("Name", name)
        args.putString("Email", email)
        frag.setArguments(args)
        fram.replace(R.id.profile_container, frag)
        fram.commit()
    }
}