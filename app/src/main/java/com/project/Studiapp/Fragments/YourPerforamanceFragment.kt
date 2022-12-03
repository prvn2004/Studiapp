package com.project.Studiapp.Fragments

import android.app.ProgressDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.project.Studiapp.databinding.FragmentYourPerforamanceBinding
import com.project.Studiapp.datafiles.DashboardDataFile
import java.util.*

class YourPerforamanceFragment : Fragment() {
    private lateinit var binding: FragmentYourPerforamanceBinding
    private lateinit var firestore: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentYourPerforamanceBinding.inflate(inflater, container, false)
        val view = binding.root
        getMarksDetailsFirebase()

        return view
    }

    fun getMarksDetailsFirebase() {
        val progressDialog = ProgressDialog(activity)
        progressDialog.setMessage("Loading")
        progressDialog.setCancelable(false)
        progressDialog.show()

        firestore = FirebaseFirestore.getInstance()
        val setno = requireArguments().getString("SETNO")
        val testdate = requireArguments().getString("TESTDATE")

        val realsetno = testdate + " Set No. " + setno
        val currentuser = FirebaseAuth.getInstance().currentUser!!.uid

        val collectionReference =
            firestore.collection("LiveTest").document(realsetno).collection("Marks")
                .document(currentuser)
        collectionReference.addSnapshotListener { value, error ->
            value?.toObject(DashboardDataFile::class.java)
            val attendence = value?.get("Attended").toString()

            if (attendence == "YES") {
                binding.colorLayout.visibility = View.VISIBLE
                binding.testMarksGot.text = value?.get("dashboardtestmarks").toString()
                binding.testDate.text = value?.get("dashboardtestdate").toString()
                binding.mocktestNumber.text = value?.get("dashboardtestnumber").toString()
            } else {
                binding.warning.visibility = View.VISIBLE
                binding.warning.text = "You Didn't Attempted this test!"
            }

            if (value == null || error != null) {
                Toast.makeText(activity, "Error fetching data", Toast.LENGTH_SHORT).show()
                return@addSnapshotListener
            }
            if (progressDialog.isShowing) progressDialog.dismiss()
        }
    }
}