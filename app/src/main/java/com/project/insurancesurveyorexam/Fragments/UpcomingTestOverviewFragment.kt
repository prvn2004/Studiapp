package com.project.insurancesurveyorexam.Fragments

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.project.insurancesurveyorexam.R
import com.project.insurancesurveyorexam.databinding.FragmentUpcomingTestOverviewBinding
import com.project.insurancesurveyorexam.datafiles.DashboardDataFile
import java.text.SimpleDateFormat
import java.util.*

class UpcomingTestOverviewFragment : Fragment() {
    private lateinit var binding: FragmentUpcomingTestOverviewBinding
    private lateinit var firestore: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUpcomingTestOverviewBinding.inflate(inflater, container, false)
        val view = binding.root

        val testName = requireArguments().getString("Title")
        val testQuestionNumber = requireArguments().getString("TotalQuestion")
        val testDuration = requireArguments().getString("TotalDuration")
        val testMarks = requireArguments().getString("TotalMarks")

        binding.ToolbarTitle.text = "$testName"
        binding.totalMarksDigit.text = testMarks + " Marks"
        binding.totalDurationDigit.text = testDuration + " Min."
        binding.totalQuestionsDigit.text = testQuestionNumber + " Qns"
        Log.d("hey", "$testDuration + $testQuestionNumber + $testMarks")
        binding.button.text = "Unlock"

        binding.button.setOnClickListener {
            showDialog()
        }
        binding.threeLines.setOnClickListener {
            showFragment(LiveTestMainFragment())
        }


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
        val timestamp = requireArguments().getLong("TIMESTAMP")
        val Duration = requireArguments().getString("TotalDuration")?.toInt()
        val finaltimestamp = timestamp + Duration!!.times(60)

        val realsetno = testdate + " Set No. " + setno
        val currentuser = FirebaseAuth.getInstance().currentUser!!.uid

        val collectionReference =
            firestore.collection("LiveTest").document(realsetno).collection("Marks")
                .document(currentuser)
        collectionReference.addSnapshotListener { value, error ->
            value?.toObject(DashboardDataFile::class.java)
            val attendence = value?.get("Attended").toString()
            val c = Calendar.getInstance()
            val hour = c.get(Calendar.HOUR_OF_DAY).toInt()
            val sdf = SimpleDateFormat("dd/MM/yyyy")
            val formatedDate = sdf.format(Date()).toString()

            val tsLong = System.currentTimeMillis()
            val ts = tsLong / 1000
            Log.d("Hey", "$timestamp $finaltimestamp $ts")


            if (attendence == "YES") {
                binding.button.isEnabled = false
                binding.button.text = "Unlock"
                binding.resulttext.visibility = View.VISIBLE
                binding.random.visibility = View.VISIBLE
                binding.random2.visibility = View.VISIBLE
                binding.marks.visibility = View.VISIBLE
                binding.Status.visibility = View.VISIBLE
                binding.warning.visibility = View.VISIBLE
                binding.warning.text = "You have already given the test!"
                binding.marks.text = value?.get("dashboardtestmarks").toString()
                binding.Status.text = value?.get("dashboardtestpassfail").toString()
            } else {
                if (ts < timestamp) {
                    binding.button.isEnabled = false
                    binding.warning.visibility = View.VISIBLE
                    binding.warning.text = "Test Not Started Yet!"
                    binding.button.text = "Unlock"
                } else if (ts > finaltimestamp) {
                    binding.button.isEnabled = false
                    binding.warning.visibility = View.VISIBLE
                    binding.warning.text = "Test is over!"
                    binding.button.text = "Unlock"
                }
            }

            if (value == null || error != null) {
                Toast.makeText(activity, "Error fetching data", Toast.LENGTH_SHORT).show()
                return@addSnapshotListener
            }
            if (progressDialog.isShowing) progressDialog.dismiss()

        }
    }

    fun showDialog() {
        val dialogBuilder = AlertDialog.Builder(activity)
        dialogBuilder.setMessage("Do you want to Start test?")
            .setCancelable(false)
            .setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, id ->
                showFragment(UpcomingTestQuestionFragment())
            })
            .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, id ->
                dialog.cancel()
            })
        val alert = dialogBuilder.create()
        alert.show()
    }

    fun showFragment(fragment: Fragment) {
        val testName = requireArguments().getString("Title")
        val id = requireArguments().getString("ID")
        val setno = requireArguments().getString("SETNO")
        val testdate = requireArguments().getString("TESTDATE")


        val testDuration = requireArguments().getString("TotalDuration")

        val fram = activity?.supportFragmentManager?.beginTransaction()
        val frag: Fragment = fragment
        val args = Bundle()
        args.putString("Title", testName)
        args.putString("ID", id)
        args.putString("Duration", testDuration)
        args.putString("SETNO", setno)
        args.putString("TESTDATE", testdate)
        frag.setArguments(args)
        fram?.replace(R.id.live_container, fragment)
        fram?.addToBackStack(null)
        fram?.commit()
    }

}