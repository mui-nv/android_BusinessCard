package com.example.businesscard.scene.main.create

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.businesscard.R
import com.example.businesscard.databinding.CreateFragmentBinding
import com.example.businesscard.scene.main.MainActivity

class CreateFragment : Fragment() {

    companion object {
        fun newInstance() = CreateFragment()
    }

    private lateinit var viewModel: CreateViewModel
    private lateinit var viewDataBinding: CreateFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = CreateFragmentBinding.inflate(inflater, container, false)
            .apply {
                viewmodel = (activity as MainActivity).obtainCreateViewModel()
            }
        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

}
