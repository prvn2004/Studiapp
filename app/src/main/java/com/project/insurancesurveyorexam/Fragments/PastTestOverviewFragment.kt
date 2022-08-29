package com.project.insurancesurveyorexam.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.project.insurancesurveyorexam.R
import com.project.insurancesurveyorexam.databinding.FragmentPastTestOverviewBinding

class PastTestOverviewFragment : Fragment() {
    private lateinit var binding: FragmentPastTestOverviewBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPastTestOverviewBinding.inflate(inflater, container, false)
        val view = binding.root

        val setno = requireArguments().getString("SETNO")
        val testdate = requireArguments().getString("TESTDATE")

        val title = testdate + " Exam Set No. " + setno
        binding.title.text = title

        binding.threeLines.setOnClickListener {
            onBackPressed()
        }

        binding.yourPerformance.setBackgroundResource(R.drawable.underline_background)
        showFragment(YourPerforamanceFragment(), setno, testdate)


        binding.yourPerformance.setOnClickListener {
            showFragment(PastTestOverviewFragment(), setno, testdate)
            binding.yourPerformance.setBackgroundResource(R.drawable.underline_background)
            binding.othersPerformance.setBackgroundResource(R.color.white)
        }
        binding.othersPerformance.setOnClickListener {
            showFragment(OthersPerformanceFragment(), setno, testdate)
            binding.othersPerformance.setBackgroundResource(R.drawable.underline_background)
            binding.yourPerformance.setBackgroundResource(R.color.white)
        }

        return view
    }

    fun showFragment(fragment: Fragment, setno: String?, testdate: String?) {
        val fram = activity?.supportFragmentManager?.beginTransaction()
        val frag = fragment
        val args = Bundle()
        args.putString("TESTDATE", testdate)
        args.putString("SETNO", setno)
        frag.setArguments(args)
        fram?.replace(R.id.container_past_test, frag)
        fram?.commit()
    }

    fun onBackPressed() {
        val fm: FragmentManager? = fragmentManager
        if (fm?.getBackStackEntryCount()!! > 0) {
            fm.popBackStack()
        } else {
        }
    }
}