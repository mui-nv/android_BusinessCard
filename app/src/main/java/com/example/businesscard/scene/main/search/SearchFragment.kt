package com.example.businesscard.scene.main.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.businesscard.data.remote.data.Information
import com.example.businesscard.databinding.SearchFragmentBinding
import com.example.businesscard.scene.main.MainActivity
import com.example.businesscard.util.Event
import com.example.businesscard.util.showAlertMessage

class SearchFragment : Fragment() {

    companion object {
        fun newInstance() = SearchFragment()
    }

    private lateinit var viewDataBinding: SearchFragmentBinding
    private lateinit var listAdapter: InformationsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = SearchFragmentBinding.inflate(inflater, container, false).apply {
            viewmodel = (activity as MainActivity).obtainSearchViewModel().apply {
                openInformationEvent.observe(activity!!, Observer<Event<Information>> {
                    it.getContentIfNotHandled()?.let {
                        (activity as MainActivity).openEditTab(it)
                    }
                })

                errorMessage.observe(activity!!, Observer<Event<String>> {
                    it.getContentIfNotHandled()?.let {
                        (activity as AppCompatActivity).showAlertMessage(it)
                    }
                })
            }
        }

        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewDataBinding.setLifecycleOwner(this.viewLifecycleOwner)

        setupAdapter()
    }

    override fun onResume() {
        super.onResume()
        viewDataBinding.viewmodel?.getDatas()
    }

    fun setupAdapter() {
        val viewModel = viewDataBinding.viewmodel

        if (viewModel != null) {
            listAdapter = InformationsAdapter(ArrayList(), viewModel)
            viewDataBinding.tasksList.adapter = listAdapter
        }
    }
}
