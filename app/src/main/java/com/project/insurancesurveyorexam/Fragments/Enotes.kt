package com.project.insurancesurveyorexam.Fragments

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.project.insurancesurveyorexam.adapters.EnotesChaptersAdapter
import com.project.insurancesurveyorexam.adapters.SyllabusAdapter
import com.project.insurancesurveyorexam.databinding.FragmentEnotesBinding
import com.project.insurancesurveyorexam.datafiles.EnotesNumberDataFile
import com.project.insurancesurveyorexam.datafiles.SyllabusDataFile


class Enotes : Fragment() {
    private lateinit var binding: FragmentEnotesBinding
    lateinit var recyclerView: RecyclerView
    private lateinit var LinkModel: ArrayList<EnotesNumberDataFile>
    private lateinit var firestore: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEnotesBinding.inflate(inflater, container, false)
        val view = binding.root
        LinkModel = arrayListOf<EnotesNumberDataFile>()

        binding.threeLines.setOnClickListener {
            activity?.finish()
        }

        getChapters()
        return view
    }

    private fun getChapters() {
        val progressDialog = ProgressDialog(activity)
        progressDialog.setMessage("Loading")
        progressDialog.setCancelable(false)
        progressDialog.show()

        firestore = FirebaseFirestore.getInstance()
        val collectionReference = firestore.collection("Enotes")
        collectionReference.addSnapshotListener { value, error ->
            if (value == null || error != null) {
                Toast.makeText(activity, "Error fetching data", Toast.LENGTH_SHORT).show()
                return@addSnapshotListener
            }
            Log.d("DATA", value.toObjects(EnotesNumberDataFile::class.java).toString())
            LinkModel.clear()
            LinkModel.addAll(value.toObjects(EnotesNumberDataFile::class.java))
            recyclerView = binding.EnotesnumberRecyclerView
            recyclerView.setHasFixedSize(true)
            recyclerView.layoutManager = GridLayoutManager(activity, 3)
            recyclerView.adapter = EnotesChaptersAdapter(LinkModel)

            if (progressDialog.isShowing) progressDialog.dismiss()

        }

    }
}