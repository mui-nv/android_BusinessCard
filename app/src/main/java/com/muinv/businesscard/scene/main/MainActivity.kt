package com.muinv.businesscard.scene.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.muinv.businesscard.R
import com.muinv.businesscard.data.remote.data.Information
import com.muinv.businesscard.scene.main.create.CreateViewModel
import com.muinv.businesscard.scene.main.edit.EditFragment
import com.muinv.businesscard.scene.main.edit.EditViewModel
import com.muinv.businesscard.scene.main.search.SearchViewModel
import com.muinv.businesscard.scene.main.ui.main.SectionsPagerAdapter
import com.muinv.businesscard.util.obtainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        view_pager.adapter = sectionsPagerAdapter
        tabs.run {
            setupWithViewPager(view_pager)
        }
    }

    fun openEditTab(infomation: Information) {
        view_pager.setCurrentItem(2, true)
        var tag = "android:switcher:" + R.id.view_pager + ":" + 2
        val fragment1 = supportFragmentManager.findFragmentByTag(tag)
        (fragment1 as EditFragment).setInformation(infomation)
    }

    //    inline fun <reified T> obtainViewModel(): T = obtainViewModel(T::class.java)
    fun obtainSearchViewModel(): SearchViewModel = obtainViewModel(SearchViewModel::class.java)

    fun obtainCreateViewModel(): CreateViewModel = obtainViewModel(CreateViewModel::class.java)
    fun obtainEditViewModel(): EditViewModel = obtainViewModel(EditViewModel::class.java)
}