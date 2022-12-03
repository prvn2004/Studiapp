package com.project.Studiapp.Fragments

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.project.Studiapp.adapters.SyllabusAdapter
import com.project.Studiapp.databinding.FragmentSyllabusBinding
import com.project.Studiapp.datafiles.SyllabusDataFile


class SyllabusFragment : Fragment() {
    private lateinit var binding: FragmentSyllabusBinding
    lateinit var recyclerView: RecyclerView
    private lateinit var LinkModel: ArrayList<SyllabusDataFile>
    private lateinit var firestore: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSyllabusBinding.inflate(inflater, container, false)
        val view = binding.root
        LinkModel = arrayListOf<SyllabusDataFile>()

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
        val collectionReference = firestore.collection("Syllabus").orderBy("No")
        collectionReference.addSnapshotListener { value, error ->
            if (value == null || error != null) {
                Toast.makeText(activity, "Error fetching data", Toast.LENGTH_SHORT).show()
                return@addSnapshotListener
            }
            Log.d("DATA", value.toObjects(SyllabusDataFile::class.java).toString())
            LinkModel.clear()
            LinkModel.addAll(value.toObjects(SyllabusDataFile::class.java))
            recyclerView = binding.SyllabusRecyclerView
            recyclerView.setHasFixedSize(true)
            recyclerView.layoutManager = GridLayoutManager(activity, 3)
            recyclerView.adapter = SyllabusAdapter(LinkModel)

            if (progressDialog.isShowing) progressDialog.dismiss()

        }

    }
}