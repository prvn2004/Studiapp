package com.project.insurancesurveyorexam.Fragments

import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.project.insurancesurveyorexam.Activities.MockTestActivity
import com.project.insurancesurveyorexam.R
import com.project.insurancesurveyorexam.databinding.FragmentResultBinding
import com.project.insurancesurveyorexam.datafiles.MockTestNumbersDataFile
import java.text.SimpleDateFormat
import java.util.*


class ResultFragment : Fragment() {
    private lateinit var binding: FragmentResultBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentResultBinding.inflate(inflater, container, false)
        val view = binding.root


        val sdf = SimpleDateFormat("dd MMM yyyy")
        val formatedDate = sdf.format(Date())

        val quizData = requireArguments().getString("QUIZ")
        val quiz =
            Gson().fromJson<MockTestNumbersDataFile>(quizData, MockTestNumbersDataFile::class.java)
        var correct = 0
        var totalQuestions = 0
        for (entry in quiz.Questions.entries) {
            val question = entry.value
            if (question.Answer == question.userAnswer) {
                correct += 1
            }
            if (question.Answer == question.Answer) {
                totalQuestions += 1
            }
        }
        val score = "$correct" + "/" + totalQuestions

        val percentcorrect = (correct * 100) / totalQuestions
        var passFail = "FAIL"
        if (percentcorrect > 20) {
            passFail = "PASS"
        } else {
            passFail = "FAIL"
        }
        val testName = requireArguments().getString("Title")
        writeMarksToFirebase(testName, formatedDate, score, "60 MIN", passFail)

        binding.threeLines.setOnClickListener {
            val intent = Intent(activity, MockTestActivity::class.java)
            requireActivity().startActivity(intent)
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            requireActivity().finish()
        }

        calulateScore()
        activity?.onBackPressedDispatcher?.addCallback(
            this.requireActivity(),
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    showDialog()
                }
            })

        binding.ReviewAnswers.setOnClickListener {
            showFragment(ReviewFragment())
        }

        return view
    }

    fun showFragment(fragment: Fragment) {
        val quizData = requireArguments().getString("QUIZ")

        val fram = activity?.supportFragmentManager?.beginTransaction()
        val frag: Fragment = fragment
        val args = Bundle()
        args.putString("QUIZ", quizData)
        frag.setArguments(args)
        fram?.replace(R.id.container_mocktest, fragment)
        fram?.addToBackStack(null)
        fram?.commit()
    }


    private fun writeMarksToFirebase(
        dashboardtestnumber: String?,
        dashboardtestdate: String?,
        dashboardtestmarks: String?,
        dashboardtesttime: String?,
        dashboardtestpassfail: String?
    ) {
        val currentTimestamp = System.currentTimeMillis().toString()
        val fireStoreDatabase = FirebaseFirestore.getInstance()
        val hashMap = hashMapOf<String, Any>(
            "dashboardtestnumber" to "$dashboardtestnumber",
            "dashboardtestdate" to "$dashboardtestdate",
            "dashboardtestmarks" to "$dashboardtestmarks",
            "dashboardtesttime" to "$dashboardtesttime",
            "dashboardtestpassfail" to "$dashboardtestpassfail"
        )
        val docPath = "Practise Test " + dashboardtestnumber

        val currentuser = FirebaseAuth.getInstance().currentUser!!.uid
        // use the add() method to create a document inside users collection
        fireStoreDatabase.collection("Marks").document(currentuser).collection("UID")
            .add(hashMap)
            .addOnSuccessListener {
//                Log.d(TAG, "Added document with ID ${it.id}")
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error adding document $exception")
            }
    }


    private fun showDialog() {
        val dialogBuilder = AlertDialog.Builder(activity)
        dialogBuilder.setMessage("Do you want to Go back to Home?")
            .setCancelable(false)
            .setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, id ->
                val intent = Intent(activity, MockTestActivity::class.java)
                requireActivity().startActivity(intent)
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                requireActivity().finish()
            })
            .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, id ->
                dialog.cancel()
            })
        val alert = dialogBuilder.create()
        alert.show()
    }

    private fun calulateScore() {
        val quizData = requireArguments().getString("QUIZ")
        val quiz =
            Gson().fromJson<MockTestNumbersDataFile>(quizData, MockTestNumbersDataFile::class.java)
        var correct = 0
        var totalQuestions = 0
        var QnsAttempted = 0
        var wrongQuestion = 0
        for (entry in quiz.Questions.entries) {
            val question = entry.value
            if (question.Answer == question.userAnswer) {
                correct += 1
            }
            if (question.Answer == question.Answer) {
                totalQuestions += 1
            }
            if (question.userAnswer.isNotEmpty()) {
                QnsAttempted += 1
            }
            if (question.userAnswer != question.Answer) {
                if (question.userAnswer.isNotEmpty()) {
                    wrongQuestion += 1
                }
            }
        }
        binding.IncorrectQuestionNumber.text = wrongQuestion.toString()
        binding.TotalQuestionsNumber.text = totalQuestions.toString()
        val PercentCorrect = (correct * 100) / totalQuestions
        val percentAttempted = (QnsAttempted * 100) / totalQuestions
        binding.percentAttempted.text = percentAttempted.toString() + "%"
        binding.scoreText.text = PercentCorrect.toString()
        binding.CorrectQuestionNumber.text = correct.toString()

    }

}