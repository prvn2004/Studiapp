package com.project.Studiapp.Fragments

import android.app.ProgressDialog
import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.project.Studiapp.databinding.FragmentProfileBinding


class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var firestore: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view = binding.root

        val Name = requireArguments().getString("Name")
        val Email = requireArguments().getString("Email")

        binding.nameField.text = Name
        binding.textName.setText(Name)
        binding.emailText.setText(Email)

        getInfo()

        val name = binding.textName
        val email = binding.emailText
        val phone = binding.phoneText
        val currentuser = FirebaseAuth.getInstance().currentUser!!.uid

        binding.updateButton.setOnClickListener {
            WriteNewUser(
                name.text.toString(),
                email.text.toString(),
                currentuser,
                phone.text.toString()
            )
        }

        binding.threeLines.setOnClickListener {
            activity?.finish()
        }

        return view
    }

    private fun WriteNewUser(personName: String, personEmail: String, uid: String, phone: String) {
        val progressDialog = ProgressDialog(activity)
        progressDialog.setMessage("Updating")
        progressDialog.setCancelable(false)
        progressDialog.show()

        val fireStoreDatabase = FirebaseFirestore.getInstance()
        val Name = personName.toString()
        val Email = personEmail.toString()
        val Phone = phone.toString()

        val hashMap = hashMapOf<String, Any>(
            "Name" to Name,
            "Email" to Email,
            "Phone" to Phone
        )

        fireStoreDatabase.collection("Students").document(uid)
            .update(hashMap)
            .addOnSuccessListener {

            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error adding document $exception")
            }

        if (progressDialog.isShowing) progressDialog.dismiss()
    }

    private fun getInfo() {
        val currentuser = FirebaseAuth.getInstance().currentUser!!.uid

        val docRef: DocumentReference =
            FirebaseFirestore.getInstance().collection("Students").document(currentuser)
        docRef.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val document = task.result

                val name = document.getString("Name")
                val email = document.getString("Email")
                val phone = document.getString("Phone")

                if (name != null && email != null && phone != null) {
                    binding.nameField.text = name
                    binding.emailText.setText(email)
                    binding.textName.setText(name)
                    binding.phoneText.setText(phone)
                } else {
                    val Name = requireArguments().getString("Name")
                    val Email = requireArguments().getString("Email")

                    binding.nameField.text = Name
                    binding.textName.setText(Name)
                    binding.emailText.setText(Email)
                }
            } else {
                Log.d("LOGGER", "get failed with ", task.exception)
            }
        }
    }
}