package com.project.insurancesurveyorexam.Fragments

import android.app.AlertDialog
import android.content.ContentValues
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.project.insurancesurveyorexam.Activities.LiveTestActivity
import com.project.insurancesurveyorexam.Activities.MockTestActivity
import com.project.insurancesurveyorexam.R
import com.project.insurancesurveyorexam.databinding.FragmentUpcomingTestResultBinding
import com.project.insurancesurveyorexam.datafiles.MockTestNumbersDataFile
import com.project.insurancesurveyorexam.datafiles.UpcomingTestDataFile
import java.text.SimpleDateFormat
import java.util.*

class UpcomingTestResultFragment : Fragment() {
    private lateinit var binding: FragmentUpcomingTestResultBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUpcomingTestResultBinding.inflate(inflater, container, false)
        val view = binding.root

        val sdf = SimpleDateFormat("dd MMM yyyy")
        val formatedDate = sdf.format(Date())

        val quizData = requireArguments().getString("QUIZ")

        val quiz =
            Gson().fromJson<UpcomingTestDataFile>(quizData, UpcomingTestDataFile::class.java)
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
            val intent = Intent(activity, LiveTestActivity::class.java)
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
        fram?.replace(R.id.live_container, fragment)
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
        val setno = requireArguments().getString("SETNO")
        val testdate = requireArguments().getString("TESTDATE")

        val acct = GoogleSignIn.getLastSignedInAccount(requireActivity().applicationContext)
        val personName = acct?.displayName.toString()

        val fireStoreDatabase = FirebaseFirestore.getInstance()
        val hashMap = hashMapOf<String, Any>(
            "dashboardtestnumber" to "$dashboardtestnumber",
            "dashboardtestdate" to "$dashboardtestdate",
            "dashboardtestmarks" to "$dashboardtestmarks",
            "dashboardtesttime" to "$dashboardtesttime",
            "dashboardtestpassfail" to "$dashboardtestpassfail",
            "Attended" to "YES",
            "PersonName" to "$personName"
        )
        val docPath = "Practise Test " + dashboardtestnumber

        val currentuser = FirebaseAuth.getInstance().currentUser!!.uid
        val title = requireArguments().getString("Title")

        // use the add() method to create a document inside users collection
        fireStoreDatabase.collection("LiveTest").document(testdate + " Set No. " + setno)
            .collection("Marks").document(currentuser)
            .set(hashMap)
            .addOnSuccessListener {
//                Log.d(TAG, "Added document with ID ${it.id}")
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error adding document $exception")
            }

        fireStoreDatabase.collection("LiveTest").document(testdate + " Set No. " + setno)
            .collection("AllStudentsMarks")
            .add(hashMap)
            .addOnSuccessListener {
//                Log.d(TAG, "Added document with ID ${it.id}")
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error adding document $exception")
            }
    }


    private fun showDialog() {
        val dialogBuilder = AlertDialog.Builder(activity)
        dialogBuilder.setMessage("Do you want to Go back to Home?")
            .setCancelable(false)
            .setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, id ->
                val intent = Intent(activity, LiveTestActivity::class.java)
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