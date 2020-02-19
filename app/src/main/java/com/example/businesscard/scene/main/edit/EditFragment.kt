package com.example.businesscard.scene.main.edit

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.businesscard.R
import com.example.businesscard.data.remote.data.Information
import com.example.businesscard.databinding.EditFragmentBinding
import com.example.businesscard.scene.main.MainActivity
import com.example.businesscard.util.getLocationFromAddress
import com.example.businesscard.util.setMapLocation
import com.example.businesscard.util.showAlertMessage
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.create_fragment.*

class EditFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    companion object {
        fun newInstance() = EditFragment()
    }

    private lateinit var viewDataBinding: EditFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = EditFragmentBinding.inflate(inflater, container, false)
            .apply {
                viewmodel = (activity as MainActivity).obtainEditViewModel().apply {
                    errorMessage.observe(activity as MainActivity, Observer {
                        it.getContentIfNotHandled()?.let {
                            (activity as AppCompatActivity).showAlertMessage(it)
                        }
                    })
                }
                textAddress1.addTextChangedListener(textWatcher)
                textAddress2.addTextChangedListener(textWatcher)
            }

        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewDataBinding.setLifecycleOwner(this.viewLifecycleOwner)

        (childFragmentManager.findFragmentById(R.id.mapAddress) as SupportMapFragment)
            .getMapAsync(this@EditFragment)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.apply {
            setOnMapLongClickListener {
                (activity as AppCompatActivity).setMapLocation(mMap, it)
                viewDataBinding.viewmodel?._information?.value?.latitude = it.latitude
                viewDataBinding.viewmodel?._information?.value?.longitude = it.longitude
            }
        }
    }

    var textWatcher = object : TextWatcher {
        var beforeAddress1 = ""
        var beforeAddress2 = ""

        @SuppressLint("CheckResult")
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            var afterAddress1 = textAddress1.text.toString()
            var afterAddress2 = textAddress2.text.toString()

            if (afterAddress1 != "" && afterAddress2 != "" &&
                (afterAddress1 != beforeAddress1 || afterAddress2 != beforeAddress2)
            ) {
                (activity as AppCompatActivity)
                    .getLocationFromAddress(afterAddress1 + afterAddress2)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        it?.let {
                            (activity as AppCompatActivity).setMapLocation(mMap, it)
                            viewDataBinding.viewmodel?._information?.value?.latitude = it.latitude
                            viewDataBinding.viewmodel?._information?.value?.longitude = it.longitude
                        }
                    }, {
                        it.printStackTrace()
                    })
            }
        }

        override fun afterTextChanged(s: Editable?) {

        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            beforeAddress1 = textAddress1.text.toString()
            beforeAddress2 = textAddress2.text.toString()
        }
    }

    fun setInformation(information: Information) {
        viewDataBinding.viewmodel?._information?.value = information
    }
}
