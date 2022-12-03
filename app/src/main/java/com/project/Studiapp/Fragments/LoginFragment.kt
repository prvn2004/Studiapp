package com.project.Studiapp.Fragments

import android.app.ProgressDialog
import android.content.ContentValues
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.project.Studiapp.Activities.HomeActivity
import com.project.Studiapp.R
import com.project.Studiapp.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var auth: FirebaseAuth
    lateinit var sharedPreferences: SharedPreferences
    lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var database: DatabaseReference
    val Req_Code: Int = 123

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root
        auth = FirebaseAuth.getInstance()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.client_id))
            .requestEmail()
            .build()

        database = FirebaseDatabase.getInstance().reference
        mGoogleSignInClient = activity?.let { GoogleSignIn.getClient(it, gso) }!!

        binding.loginButton.setOnClickListener {
            signInGoogle()
        }

        val acct = GoogleSignIn.getLastSignedInAccount(requireActivity())
        if (acct != null) {
            val personName = acct.displayName
            Toast.makeText(activity, "signed-in as $personName", Toast.LENGTH_SHORT).show()
        }

        return view
    }

//    override fun onStart() {
//        super.onStart()
//        val account = activity?.let { GoogleSignIn.getLastSignedInAccount(it) }
//        account?.let { UpdateUI(it) }
//    }

    private fun signInGoogle() {
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, Req_Code)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Req_Code) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleResult(task)
        }
    }

    private fun handleResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account: GoogleSignInAccount = completedTask.getResult(ApiException::class.java)
            UpdateUI(account)
        } catch (e: ApiException) {
//            Toast.makeText(activity, e.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    private fun UpdateUI(account: GoogleSignInAccount) {
        val progressDialog = ProgressDialog(activity)
        progressDialog.setMessage("Logging In")
        progressDialog.setCancelable(false)
        progressDialog.show()
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val preferences = PreferenceManager.getDefaultSharedPreferences(activity)
                val editor = preferences.edit()
                editor.putString("login", "Login")
                val acct = GoogleSignIn.getLastSignedInAccount(requireActivity())
                if (acct != null) {
                    val personName = acct.displayName.toString()
                    val personEmail = acct.email.toString()
                    val uid = FirebaseAuth.getInstance().currentUser?.uid.toString()
                    WriteNewUser(personName, personEmail, uid)
                }
                editor.apply()
                val intent = Intent(activity, HomeActivity::class.java)
                startActivity(intent)
                activity?.finish()
                if (progressDialog.isShowing) progressDialog.dismiss()
            }
        }
        if (progressDialog.isShowing) progressDialog.dismiss()

    }

    private fun WriteNewUser(personName: String, personEmail: String, uid: String) {
        val fireStoreDatabase = FirebaseFirestore.getInstance()

        val hashMap = hashMapOf<String, Any>(
            "Name" to "$personName",
            "Email" to "$personEmail",
            "Phone" to ""
        )

        fireStoreDatabase.collection("Students").document(uid)
            .set(hashMap)
            .addOnSuccessListener {
//                Log.d(TAG, "Added document with ID ${it.id}")
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error adding document $exception")
            }
    }


}