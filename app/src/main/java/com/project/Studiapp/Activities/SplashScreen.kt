package com.project.Studiapp.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.project.Studiapp.Fragments.SplashFragment
import com.project.Studiapp.R

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        actionBar?.hide()
        showFragment(SplashFragment())

    }
    fun showFragment(fragment: Fragment) {
        val fram = supportFragmentManager.beginTransaction()
        fram.replace(R.id.splash_container, fragment)
        fram.commit()
    }
}