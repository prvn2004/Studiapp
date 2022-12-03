package com.project.Studiapp.Activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.Gravity
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ShareCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.navigation.NavigationView
import com.google.firebase.firestore.FirebaseFirestore
import com.project.Studiapp.R
import com.project.Studiapp.databinding.ActivityHomeBinding
import com.project.Studiapp.datafiles.DashboardDataFile
import java.util.*


class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var firestore: FirebaseFirestore
    private lateinit var actionBarToggle: ActionBarDrawerToggle
    private lateinit var navView: NavigationView
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient = this.let { GoogleSignIn.getClient(it, gso) }

        drawerLayout = binding.drawerLayout
        val acct = GoogleSignIn.getLastSignedInAccount(this)
        val personName = acct?.displayName.toString()
        val email = acct?.email.toString()
        if (acct != null) {
            val header: View = binding.navView.getHeaderView(0)
            val mNameTextView = header.findViewById(R.id.nameText2) as TextView
            mNameTextView.text = personName
            val fullName = "hey " + personName
            binding.nameText.text = fullName
        } else {
            Toast.makeText(this, "No Internet", Toast.LENGTH_SHORT).show()
        }

        actionBarToggle = ActionBarDrawerToggle(this, drawerLayout, 0, 0)
        drawerLayout.addDrawerListener(actionBarToggle)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        actionBarToggle.syncState()

        binding.threeLines.setOnClickListener {
            drawerLayout.openDrawer(Gravity.LEFT)
        }
        selectingItems(personName, email)

        binding.EnotesButton.setOnClickListener {
            val intent = Intent(this, EnotesActivity::class.java)
            startActivity(intent)
        }
        binding.button3.setOnClickListener {
            val intent = Intent(this, MockTestActivity::class.java)
            startActivity(intent)
        }
        binding.performanceButton.setOnClickListener {
            val intent = Intent(this, DashboardActivity::class.java)
            intent.putExtra("Name", personName)
            startActivity(intent)
        }
        binding.SyllabusButton.setOnClickListener {
            val intent = Intent(this, SyllabusActivity::class.java)
            startActivity(intent)
        }
        binding.button4.setOnClickListener {
            val intent = Intent(this, LiveTestActivity::class.java)
            startActivity(intent)
        }
        binding.shareApp.setOnClickListener {
            getAppShareLink()
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        drawerLayout.openDrawer(navView)
        return true
    }

    // override the onBackPressed() function to close the Drawer when the back button is clicked
    override fun onBackPressed() {
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    private fun selectingItems(name: String, email: String) {
        binding.navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.report -> {
                    report()
                    true
                }
                R.id.signOut -> {
                    signOut()
                    true
                }
                R.id.myProfile -> {
                    val intent = Intent(this, ProfileActivity::class.java)
                    intent.putExtra("Name", name)
                    intent.putExtra("Email", email)
                    startActivity(intent)
                    true
                }
                R.id.share ->{
                    getAppShareLink()
                    true
                }
                else -> {
                    false
                }
            }
        }
    }

    private fun report() {
        val browserIntent =
            Intent(Intent.ACTION_VIEW, Uri.parse("https://forms.gle/KmxwMQS8RVDWoVdc8"))
        startActivity(browserIntent)
    }

    private fun goToAppLink(AppLink: String){
        ShareCompat.IntentBuilder.from(this)
            .setType("text/plain")
            .setChooserTitle("Chooser title")
            .setText( AppLink)
            .startChooser();
    }

    private fun getAppShareLink() {
        firestore = FirebaseFirestore.getInstance()

        val collectionReference =
            firestore.collection("AppShareLink").document("Link")
        collectionReference.addSnapshotListener { value, error ->
            value?.toObject(DashboardDataFile::class.java)
           val AppLink = value?.get("Link").toString()

            goToAppLink(AppLink)

            if (value == null || error != null) {
                Toast.makeText(this, "Error fetching data", Toast.LENGTH_SHORT).show()
                return@addSnapshotListener
            }
        }
    }

    private fun signOut() {
//        val progressDialog = ProgressDialog(this)
//        progressDialog.setMessage("SigningOut Please Wait")
//        progressDialog.setCancelable(false)
//        progressDialog.show()
        this.let {
            mGoogleSignInClient.signOut()
                .addOnCompleteListener(it, OnCompleteListener<Void?> {

                    val preferences = PreferenceManager.getDefaultSharedPreferences(this)
                    val editor = preferences.edit()
                    editor.clear()
                    editor.apply()

                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    this.finish()

                })
        }
//        if (progressDialog.isShowing) progressDialog.dismiss()
    }

}