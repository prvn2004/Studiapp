package com.project.insurancesurveyorexam.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.project.insurancesurveyorexam.Fragments.SyllabusFragment
import com.project.insurancesurveyorexam.R
import com.project.insurancesurveyorexam.databinding.ActivitySyllabusBinding

class SyllabusActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySyllabusBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySyllabusBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)

        showFragment(SyllabusFragment())
    }

    fun showFragment(fragment: Fragment) {
        val fram = supportFragmentManager.beginTransaction()
        val frag: Fragment = fragment
//        val args = Bundle()
//        args.putString("Name", name)
//        frag.setArguments(args)
        fram.replace(R.id.syllabus_container, frag)
        fram.commit()
    }

}