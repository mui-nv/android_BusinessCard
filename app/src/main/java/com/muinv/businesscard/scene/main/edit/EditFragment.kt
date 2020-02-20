package com.muinv.businesscard.scene.main.edit

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.muinv.businesscard.R
import com.muinv.businesscard.data.remote.data.Information
import com.muinv.businesscard.databinding.EditFragmentBinding
import com.muinv.businesscard.scene.main.MainActivity
import com.muinv.businesscard.util.getLocationFromAddress
import com.muinv.businesscard.util.setMapLocation
import com.muinv.businesscard.util.showAlertMessage
import io.reactivex.Emitter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.create_fragment.*
import java.util.concurrent.TimeUnit

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
            }

        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewDataBinding.setLifecycleOwner(this.viewLifecycleOwner)

        (childFragmentManager.findFragmentById(R.id.mapAddress) as SupportMapFragment)
            .getMapAsync(this@EditFragment)

        findLocationByAddress()
    }

    @SuppressLint("CheckResult")
    fun findLocationByAddress() {
        var observable = Observable.create<String> { emitter ->
            addtextChange(textAddress1, emitter)
            addtextChange(textAddress2, emitter)
        }

        observable.debounce(1, TimeUnit.SECONDS)
            .flatMap {
                return@flatMap (activity as AppCompatActivity)
                    .getLocationFromAddress(it)
            }
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

    fun addtextChange(editText: EditText, emitter: Emitter<String>) {
        editText.addTextChangedListener(object : TextWatcher {
            var beforeAddress1 = ""
            var beforeAddress2 = ""

            @SuppressLint("CheckResult")
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                var afterAddress1 = textAddress1.text.toString()
                var afterAddress2 = textAddress2.text.toString()

                if (afterAddress1 != "" && afterAddress2 != "" &&
                    (afterAddress1 != beforeAddress1 || afterAddress2 != beforeAddress2)
                ) {
                    emitter.onNext(afterAddress1 + "" + beforeAddress2)
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                beforeAddress1 = textAddress1.text.toString()
                beforeAddress2 = textAddress2.text.toString()
            }
        })
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.apply {
            setOnMapLongClickListener {
                (activity as AppCompatActivity).setMapLocation(mMap, it)
                viewDataBinding.viewmodel?._location?.value = it
            }
        }
    }

    fun setInformation(information: Information) {
        viewDataBinding.viewmodel?._information?.value = information
    }
}
