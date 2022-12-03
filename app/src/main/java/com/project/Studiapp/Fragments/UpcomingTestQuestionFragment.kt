package com.project.Studiapp.Fragments

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.DialogInterface
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.project.Studiapp.R
import com.project.Studiapp.adapters.QuestionAdapter
import com.project.Studiapp.databinding.FragmentUpcomingTestQuestionBinding
import com.project.Studiapp.datafiles.Question
import com.project.Studiapp.datafiles.UpcomingTestDataFile

class UpcomingTestQuestionFragment : Fragment() {
    private lateinit var binding: FragmentUpcomingTestQuestionBinding
    var quizzes: MutableList<UpcomingTestDataFile>? = null
    var question: MutableMap<String, Question>? = null
    var index = 1
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUpcomingTestQuestionBinding.inflate(inflater, container, false)
        val view = binding.root

        TickTimer()

//        binding.QuestionListOpener.setOnClickListener {
//            showHideFragment(QuestionListFragment())
//       }

        binding.submitButton.setOnClickListener {
            showDialog()
        }

        setUpEventListener()

        onBackPress()

        setupFirebase()


        return view
    }
    private fun TickTimer() {
        val testDuration = requireArguments().getString("Duration")?.toInt()
        val TimeinMilli: Long = testDuration?.times(60000)?.toLong()!!
        object : CountDownTimer(TimeinMilli, 1000) {

            override fun onTick(millisUntilFinished: Long) {

                val time = millisUntilFinished

                val hours = time % 86400 / 3600
                val minutes = (time / 1000 / 60)
                val second = time / 1000 % 60
                if (minutes < 10) {
                    if (second < 10) {
                        val newminute = "0" + minutes
                        val newsecond = "0" + second
                        binding.progressTime.setText("$newminute:$newsecond")
                    } else {
                        val newminute = "0" + minutes
                        val newsecond = second
                        binding.progressTime.setText("$newminute:$newsecond")
                    }
                } else {
                    if (second < 10) {
                        val newminute = minutes
                        val newsecond = "0" + second
                        binding.progressTime.setText("$newminute:$newsecond")
                    } else {
                        val newminute = minutes
                        val newsecond = second
                        binding.progressTime.setText("$newminute:$newsecond")
                    }
                }


            }

            override fun onFinish() {
                binding.progressTime.setText("Time Over!")
                Toast.makeText(activity, "Time Over", Toast.LENGTH_SHORT).show()
                showFragment(UpcomingTestResultFragment())
            }
        }.start()
    }

    private fun onBackPress() {
        activity?.onBackPressedDispatcher?.addCallback(
            this.requireActivity(),
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    showDialog()
                }
            })
    }

    private fun showHideFragment(fragment: Fragment) {
        val fram = activity?.supportFragmentManager?.beginTransaction()
        val frag: Fragment = fragment
        val args = Bundle()
        Log.d("FINALQUIZ", question.toString())
        val json: String = Gson().toJson(quizzes!![0])
        args.putString("QUIZ", json)
        args.putInt("Index_number", index - 1)
        frag.setArguments(args)
        fram?.add(R.id.container_mocktest, fragment)
        fram?.commit()
    }

    private fun showDialog() {
        val dialogBuilder = AlertDialog.Builder(activity)
        dialogBuilder.setMessage("Do you want to Submit test?")
            .setCancelable(false)
            .setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, id ->
                showFragment(UpcomingTestResultFragment())
            }
            )
            .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, id ->
                dialog.cancel()
            })
        val alert = dialogBuilder.create()
        alert.show()
    }

    private fun showFragment(fragment: Fragment) {
        val testName = requireArguments().getString("Title")
        val setno = requireArguments().getString("SETNO")
        val testdate = requireArguments().getString("TESTDATE")


        val fram = activity?.supportFragmentManager?.beginTransaction()
        val frag: Fragment = fragment
        val args = Bundle()
        Log.d("FINALQUIZ", question.toString())
        val json: String = Gson().toJson(quizzes!![0])
        args.putString("QUIZ", json)
        args.putString("Title", testName)
        args.putString("SETNO", setno)
        args.putString("TESTDATE", testdate)
        frag.setArguments(args)
        fram?.replace(R.id.live_container, fragment)
        fram?.addToBackStack(null)
        fram?.commit()
    }

    private fun setUpEventListener() {
        binding.prevButton.setOnClickListener {
            index--
            bindQuestions()
        }

        binding.nextButton.setOnClickListener {
            index++
            bindQuestions()
        }
    }

    private fun setupFirebase() {
        val progressDialog = ProgressDialog(activity)
        progressDialog.setMessage("Loading Question")
        progressDialog.setCancelable(false)
        progressDialog.show()
        val testName = requireArguments().getString("Title")
        val id = requireArguments().getString("ID")

        val firebase = FirebaseFirestore.getInstance()
        firebase.collection("LiveTest").whereEqualTo("MockTestTitle", id).get()
            .addOnSuccessListener {
                quizzes = it.toObjects(UpcomingTestDataFile::class.java)
                question = quizzes!![0].Questions
                bindQuestions()
                if (progressDialog.isShowing) progressDialog.dismiss()
            }


    }

    private fun bindQuestions() {

        binding.prevButton.visibility = View.GONE
        binding.nextButton.visibility = View.GONE

        if (index == 1) {
            binding.nextButton.visibility = View.VISIBLE
        } else if (index < question!!.size && index > 1) {
            binding.nextButton.visibility = View.VISIBLE
            binding.prevButton.visibility = View.VISIBLE
        } else {
            binding.prevButton.visibility = View.VISIBLE
        }

        val question = question!!["Question$index"]
        binding.QuestionNumber.text = "Question $index"
        question?.let {
            binding.questionTitle.text = question.QuestionTitle
            val optionAdapter = QuestionAdapter(requireActivity(), question)
            binding.optionRecyclerview.layoutManager = LinearLayoutManager(activity)
            binding.optionRecyclerview.adapter = optionAdapter
            binding.optionRecyclerview.setHasFixedSize(true)
        }

    }

}