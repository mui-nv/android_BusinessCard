package com.example.businesscard.scene.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.viewpager.widget.ViewPager
import com.example.businesscard.R
import com.example.businesscard.scene.main.search.SearchViewModel
import com.example.businesscard.scene.main.ui.main.SectionsPagerAdapter
import com.example.businesscard.util.obtainViewModel
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.run {
            setupWithViewPager(viewPager)
        }
    }

    //    inline fun <reified T> obtainViewModel(): T = obtainViewModel(T::class.java)
    fun obtainSearchViewModel(): SearchViewModel = obtainViewModel(SearchViewModel::class.java)
}