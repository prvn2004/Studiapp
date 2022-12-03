package com.project.Studiapp.Fragments

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
import com.project.Studiapp.adapters.DashboardAdapter
import com.project.Studiapp.databinding.FragmentDashboardBinding
import com.project.Studiapp.datafiles.DashboardDataFile

class DashboardFragment : Fragment() {
    private lateinit var binding: FragmentDashboardBinding
    lateinit var recyclerView: RecyclerView
    private lateinit var LinkModel: ArrayList<DashboardDataFile>
    private lateinit var firestore: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val view = binding.root

        val Name = requireArguments().getString("Name")
        binding.GrettingText.text = "Hey $Name"

        binding.threeLines.setOnClickListener {
            activity?.finish()
        }

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

        val collectionReference =
            firestore.collection("Marks").document(currentuser).collection("UID")
                .orderBy("dashboardtestnumber", Query.Direction.DESCENDING)
        collectionReference.addSnapshotListener { value, error ->
            if (value == null || error != null) {
                Toast.makeText(activity, "Error fetching data", Toast.LENGTH_SHORT).show()
                return@addSnapshotListener
            }
            Log.d("DATA", value.toObjects(DashboardDataFile::class.java).toString())
            LinkModel.clear()
            LinkModel.addAll(value.toObjects(DashboardDataFile::class.java))
            recyclerView = binding.recyclerviewDashboard
            recyclerView.setHasFixedSize(true)
            recyclerView.layoutManager = LinearLayoutManager(activity)
            recyclerView.adapter = DashboardAdapter(LinkModel)

            if (progressDialog.isShowing) progressDialog.dismiss()

        }

    }
}