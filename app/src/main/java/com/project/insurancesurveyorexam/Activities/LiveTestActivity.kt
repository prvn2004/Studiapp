package com.project.insurancesurveyorexam.Activities

import android.icu.text.CaseMap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.project.insurancesurveyorexam.Fragments.LiveTestMainFragment
import com.project.insurancesurveyorexam.Fragments.UpcomingTestFragment
import com.project.insurancesurveyorexam.R
import com.project.insurancesurveyorexam.databinding.ActivityLiveTestBinding
import kotlin.concurrent.timer

class LiveTestActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLiveTestBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLiveTestBinding.inflate(layoutInflater)
        val view = binding.root
        super.onCreate(savedInstanceState)
        setContentView(view)

        showFragment(LiveTestMainFragment())

    }

    fun showFragment(fragment: Fragment) {
        val fram = supportFragmentManager.beginTransaction()
        fram.replace(R.id.live_container, fragment)
        fram.commit()
    }

   fun switchContent(
        id: Int,
        fragment: Fragment,
        title: String,
        totalQuestion: String,
        totalDuration: String,
        totalMarks: String,
        testTime: String,
        ID: String,
        setno: String,
        testdate: String,
        timstamp: Long
    ) {

       val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
       val frag: Fragment = fragment
       val args = Bundle()
       args.putString("Title", title)
       args.putString("TotalQuestion", totalQuestion)
       args.putString("TotalDuration", totalDuration)
       args.putString("TotalMarks", totalMarks)
       args.putString("TestTime", testTime)
       args.putString("ID", ID)
       args.putString("SETNO", setno)
       args.putString("TESTDATE", testdate)
       args.putLong("TIMESTAMP", timstamp)
       frag.setArguments(args)
       ft.replace(id, frag, frag.toString())
       ft.addToBackStack(null)
       ft.commit()

    }
}