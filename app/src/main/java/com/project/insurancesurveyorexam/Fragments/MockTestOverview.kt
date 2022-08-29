package com.project.insurancesurveyorexam.Fragments

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.DialogInterface
import android.icu.text.CaseMap
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.project.insurancesurveyorexam.R
import com.project.insurancesurveyorexam.databinding.FragmentMockTestOverviewBinding

class MockTestOverview : Fragment() {
    private lateinit var binding: FragmentMockTestOverviewBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMockTestOverviewBinding.inflate(inflater, container, false)
        val view = binding.root
        val testName = requireArguments().getString("Title")
        val testQuestionNumber = requireArguments().getString("TotalQuestion")
        val testDuration = requireArguments().getString("TotalDuration")
        val testMarks = requireArguments().getString("TotalMarks")

        binding.ToolbarTitle.text = "Practise Test " + testName
        binding.totalMarksDigit.text = testMarks
        binding.totalDurationDigit.text = testDuration
        binding.totalQuestionsDigit.text = testQuestionNumber
        Log.d("hey", "$testDuration + $testQuestionNumber + $testMarks")


        binding.button.setOnClickListener {

            showDialog()
        }
        binding.threeLines.setOnClickListener {
            showFragment(Mocktest())
        }

        return view
    }
    fun showDialog() {
        val dialogBuilder = AlertDialog.Builder(activity)
        dialogBuilder.setMessage("Do you want to Start test?")
            .setCancelable(false)
            .setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, id ->
                showFragment(QuestionFragment())
            })
            .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, id ->
                dialog.cancel()
            })
        val alert = dialogBuilder.create()
        alert.show()
    }

    fun showFragment(fragment: Fragment) {
        val testName = requireArguments().getString("Title")
        val testDuration = requireArguments().getString("TotalDuration")

        val fram = activity?.supportFragmentManager?.beginTransaction()
        val frag: Fragment = fragment
        val args = Bundle()
        args.putString("Title", testName)
        args.putString("Duration", testDuration)
        frag.setArguments(args)
        fram?.replace(R.id.container_mocktest, fragment)
        fram?.addToBackStack(null)
        fram?.commit()
    }

    fun BackFrag(fragment: Fragment){
        val fram = activity?.supportFragmentManager?.beginTransaction()
        fram?.replace(R.id.container_mocktest, fragment)
        fram?.commit()
    }

}