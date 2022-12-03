package com.project.Studiapp.Fragments

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.project.Studiapp.adapters.MockTestChaptersAdapter
import com.project.Studiapp.databinding.FragmentMocktestBinding
import com.project.Studiapp.datafiles.MockTestNumbersDataFile

class Mocktest : Fragment() {
    private lateinit var binding: FragmentMocktestBinding
    lateinit var recyclerView: RecyclerView
    private lateinit var LinkModel: ArrayList<MockTestNumbersDataFile>
    private lateinit var firestore: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMocktestBinding.inflate(inflater, container, false)
        val view = binding.root
        LinkModel = arrayListOf<MockTestNumbersDataFile>()

        binding.threeLines.setOnClickListener {
            activity?.finish()
        }

        getTests()

        return view
    }

    private fun getTests() {
        val progressDialog = ProgressDialog(activity)
        progressDialog.setMessage("Loading")
        progressDialog.setCancelable(false)
        progressDialog.show()

        firestore = FirebaseFirestore.getInstance()
        val collectionReference = firestore.collection("MockTests").orderBy("MockTestTitle")
        collectionReference.addSnapshotListener { value, error ->
            if (value == null || error != null) {
                Toast.makeText(activity, "Error fetching data", Toast.LENGTH_SHORT).show()
                return@addSnapshotListener
            }
            Log.d("DATA", value.toObjects(MockTestNumbersDataFile::class.java).toString())
            LinkModel.clear()
            LinkModel.addAll(value.toObjects(MockTestNumbersDataFile::class.java))
            recyclerView = binding.MockTestRecyclerView
            recyclerView.setHasFixedSize(true)
            recyclerView.layoutManager = GridLayoutManager(activity, 2)
            recyclerView.adapter = MockTestChaptersAdapter(LinkModel)

            if (progressDialog.isShowing) progressDialog.dismiss()

        }

    }
}