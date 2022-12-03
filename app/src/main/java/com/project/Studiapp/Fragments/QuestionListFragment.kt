package com.project.Studiapp.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.project.Studiapp.adapters.QuestionListAdapter
import com.project.Studiapp.databinding.FragmentQuestionListBinding
import com.project.Studiapp.datafiles.QuestionListDataFile

class QuestionListFragment : Fragment() {
    private lateinit var binding: FragmentQuestionListBinding
    private lateinit var LinkModel: ArrayList<QuestionListDataFile>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQuestionListBinding.inflate(inflater, container, false)
        val view = binding.root
        LinkModel = arrayListOf()

        val link1 = QuestionListDataFile(1)

        LinkModel.add(link1)
        LinkModel.add(link1)
        LinkModel.add(link1)
        LinkModel.add(link1)
        LinkModel.add(link1)
        LinkModel.add(link1)
        LinkModel.add(link1)
        LinkModel.add(link1)
        LinkModel.add(link1)
        LinkModel.add(link1)
        LinkModel.add(link1)
        LinkModel.add(link1)
        LinkModel.add(link1)
        LinkModel.add(link1)
        LinkModel.add(link1)
        LinkModel.add(link1)
        val indexNumber = requireArguments().getInt("Index_number")
        val quizData = requireArguments().getString("QUIZ")


        val recyclerView = binding.RecyclerView
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = GridLayoutManager(activity, 6)
        recyclerView.adapter = QuestionListAdapter(LinkModel, indexNumber, quizData)

        return view
    }

}