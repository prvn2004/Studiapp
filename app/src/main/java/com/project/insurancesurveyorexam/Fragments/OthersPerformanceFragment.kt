package com.project.insurancesurveyorexam.Fragments

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.project.insurancesurveyorexam.R
import com.project.insurancesurveyorexam.adapters.DashboardAdapter
import com.project.insurancesurveyorexam.adapters.OthersPerformanceAdapter
import com.project.insurancesurveyorexam.databinding.FragmentOthersPerformanceBinding
import com.project.insurancesurveyorexam.datafiles.DashboardDataFile
import com.project.insurancesurveyorexam.datafiles.UpcomingTestDataFile

class OthersPerformanceFragment : Fragment() {
    private lateinit var binding: FragmentOthersPerformanceBinding
    lateinit var recyclerView: RecyclerView
    private lateinit var LinkModel: ArrayList<DashboardDataFile>
    private lateinit var firestore: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOthersPerformanceBinding.inflate(inflater, container, false)
        val view = binding.root

        LinkModel = arrayListOf<DashboardDataFile>()

        getTests()

        return view
    }

    private fun getTests() {
        val progressDialog = ProgressDialog(activity)
        progressDialog.setMessage("Loading")
        progressDialog.setCancelable(false)
        progressDialog.show()

        firestore = FirebaseFirestore.getInstance()
        val currentuser = FirebaseAuth.getInstance().currentUser!!.uid
        val setno = requireArguments().getString("SETNO")
        val testdate = requireArguments().getString("TESTDATE")
        val realsetno = testdate + " Set No. " + setno


        val collectionReference =
            firestore.collection("LiveTest").document(realsetno).collection("AllStudentsMarks")
                .orderBy("dashboardtestmarks", Query.Direction.DESCENDING)
        collectionReference.addSnapshotListener { value, error ->
            if (value == null || error != null) {
                Toast.makeText(activity, "Error fetching data", Toast.LENGTH_SHORT).show()
                return@addSnapshotListener
            }
            Log.d("DATA", value.toObjects(DashboardDataFile::class.java).toString())
            LinkModel.clear()
            LinkModel.addAll(value.toObjects(DashboardDataFile::class.java))
            recyclerView = binding.otherPerformanceRecyclerview
            recyclerView.setHasFixedSize(true)
            recyclerView.layoutManager = LinearLayoutManager(activity)
            recyclerView.adapter = OthersPerformanceAdapter(LinkModel)

            if (progressDialog.isShowing) progressDialog.dismiss()

        }

    }

}