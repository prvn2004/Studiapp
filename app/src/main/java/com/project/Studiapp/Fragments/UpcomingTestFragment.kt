package com.project.Studiapp.Fragments

import android.app.ProgressDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.project.Studiapp.adapters.UpcomingTestAdapter
import com.project.Studiapp.databinding.FragmentUpcomingTestBinding
import com.project.Studiapp.datafiles.UpcomingTestDataFile

class UpcomingTestFragment : Fragment() {
    private lateinit var binding: FragmentUpcomingTestBinding
    lateinit var recyclerView: RecyclerView
    private lateinit var LinkModel: ArrayList<UpcomingTestDataFile>
    private lateinit var firestore: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUpcomingTestBinding.inflate(inflater, container, false)
        val view = binding.root
        LinkModel = arrayListOf<UpcomingTestDataFile>()

        getTests()

        return view
    }

    private fun getTests() {
        val progressDialog = ProgressDialog(activity)
        progressDialog.setMessage("Loading")
        progressDialog.setCancelable(false)
        progressDialog.show()

        firestore = FirebaseFirestore.getInstance()

        val collectionReference = firestore.collection("LiveTest")
        collectionReference.addSnapshotListener { value, error ->
            if (value == null || error != null) {
                Toast.makeText(activity, "Error fetching data", Toast.LENGTH_SHORT).show()
                return@addSnapshotListener
            }
            LinkModel.clear()
            LinkModel.addAll(value.toObjects(UpcomingTestDataFile::class.java))
            recyclerView = binding.upcomingtestRecyclerview
            recyclerView.setHasFixedSize(true)
            recyclerView.layoutManager = GridLayoutManager(activity, 2)
            recyclerView.adapter = UpcomingTestAdapter(LinkModel)

            if (progressDialog.isShowing) progressDialog.dismiss()
        }

    }

}