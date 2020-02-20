package com.muinv.businesscard.scene.main.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.muinv.businesscard.data.remote.data.Information
import com.muinv.businesscard.databinding.SearchFragmentBinding
import com.muinv.businesscard.scene.main.MainActivity
import com.muinv.businesscard.util.Event
import com.muinv.businesscard.util.showAlertMessage

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
