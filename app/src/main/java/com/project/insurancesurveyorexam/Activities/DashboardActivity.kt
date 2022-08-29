package com.project.insurancesurveyorexam.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.project.insurancesurveyorexam.Fragments.DashboardFragment
import com.project.insurancesurveyorexam.R
import com.project.insurancesurveyorexam.databinding.ActivityDashboardBinding

class DashboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        showFragment(DashboardFragment())

    }

    fun showFragment(fragment: Fragment) {
       val name =  intent.getStringExtra("Name")
        val fram = supportFragmentManager.beginTransaction()
        val frag: Fragment = fragment
        val args = Bundle()
        args.putString("Name", name)
        frag.setArguments(args)
        fram.replace(R.id.DashboardContainer, frag)
        fram.commit()
    }
}