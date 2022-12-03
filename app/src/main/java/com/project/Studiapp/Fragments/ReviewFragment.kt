package com.project.Studiapp.Fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.project.Studiapp.Activities.MockTestActivity
import com.project.Studiapp.adapters.ReviewAdapter
import com.project.Studiapp.databinding.FragmentReviewBinding
import com.project.Studiapp.datafiles.MockTestNumbersDataFile
import com.project.Studiapp.datafiles.Question

class ReviewFragment : Fragment() {
    private lateinit var binding: FragmentReviewBinding
    var index = 1
    var question: MutableMap<String, Question>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentReviewBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.threeLines.setOnClickListener {
            val intent = Intent(activity, MockTestActivity::class.java)
            requireActivity().startActivity(intent)
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            requireActivity().finish()
        }

        val quizData = requireArguments().getString("QUIZ")
        setUpEventListener()
        val quiz =
            Gson().fromJson<MockTestNumbersDataFile>(quizData, MockTestNumbersDataFile::class.java)
        question = quiz.Questions
        bindQuestions()


        return view
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
        binding.questionNumber.text = "Question $index"
        question?.let {
            var correctans = question.Answer
            binding.questionTitle.text = question.QuestionTitle
            binding.correctAnswer.text = correctans
            val optionAdapter = ReviewAdapter(requireActivity(), question)
            binding.optionRecyclerview.layoutManager = LinearLayoutManager(activity)
            binding.optionRecyclerview.adapter =optionAdapter
            binding.optionRecyclerview.setHasFixedSize(true)
        }

    }

}