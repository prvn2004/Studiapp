package com.project.Studiapp.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.project.Studiapp.Fragments.Mocktest
import com.project.Studiapp.Fragments.QuestionFragment
import com.project.Studiapp.R
import com.project.Studiapp.databinding.ActivityMockTestBinding


class MockTestActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMockTestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMockTestBinding.inflate(layoutInflater)
        val view = binding.root
        showFragment(Mocktest())

        setContentView(view)
    }

    fun showFragment(fragment: Fragment) {
        val fram = supportFragmentManager.beginTransaction()
        fram.replace(R.id.container_mocktest, fragment)
        fram.commit()
    }
    fun switchContent(id: Int, fragment: Fragment, Title: String, TotalQuestion: String, TotalDuration: String, TotalMarks: String) {
        val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
        val frag: Fragment = fragment
        val args = Bundle()
        args.putString("Title", Title)
        args.putString("TotalQuestion", TotalQuestion)
        args.putString("TotalDuration", TotalDuration)
        args.putString("TotalMarks", TotalMarks)
        frag.setArguments(args)
        ft.replace(id, frag, frag.toString())
        ft.addToBackStack(null)
        ft.commit()
    }

    fun switchContentForList(id: Int, fragment: Fragment, indexNo: Int) {
        val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
        val frag: Fragment = fragment
        val args = Bundle()
        args.putInt("indexNo", indexNo)
        frag.setArguments(args)
        ft.hide(QuestionFragment())
        ft.add(id, frag)
        ft.addToBackStack(null)
        ft.commit()
    }
}