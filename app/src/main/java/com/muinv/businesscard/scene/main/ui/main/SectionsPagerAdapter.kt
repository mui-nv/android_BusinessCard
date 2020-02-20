package com.muinv.businesscard.scene.main.ui.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.muinv.businesscard.R
import com.muinv.businesscard.scene.main.create.CreateFragment
import com.muinv.businesscard.scene.main.edit.EditFragment
import com.muinv.businesscard.scene.main.search.SearchFragment

private val TAB_TITLES = arrayOf(
    R.string.tab_text_1,
    R.string.tab_text_2,
    R.string.tab_text_3
)

enum class MAIN_TAB(val value: Int) {
    CREATE(0), SEARCH(1), EDIT(2)
}

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        when (position) {
            0 -> return CreateFragment.newInstance()
            1 -> return SearchFragment.newInstance()
            else -> return EditFragment.newInstance()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        return 3
    }
}