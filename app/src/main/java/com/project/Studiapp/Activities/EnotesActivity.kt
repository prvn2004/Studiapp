package com.project.Studiapp.Activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.project.Studiapp.Fragments.Enotes
import com.project.Studiapp.R
import com.project.Studiapp.databinding.ActivityEnotesBinding

class EnotesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEnotesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEnotesBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        showFragment(Enotes())
    }

    fun showFragment(fragment: Fragment) {
        val fram = supportFragmentManager.beginTransaction()
        fram.replace(R.id.container_enotes, fragment)
        fram.commit()
    }
    fun switchContent(id: Int, fragment: Fragment) {
        val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
        ft.replace(id, fragment, fragment.toString())
        ft.addToBackStack(null)
        ft.commit()
    }
}