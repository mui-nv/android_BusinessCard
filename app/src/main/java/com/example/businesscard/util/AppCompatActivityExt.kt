package com.example.businesscard.util

import android.location.Address
import android.location.Geocoder
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import io.reactivex.Observable


fun <T : ViewModel> AppCompatActivity.obtainViewModel(viewModelClass: Class<T>) =
    ViewModelProviders.of(this, ViewModelFactory.getInstance(application)).get(viewModelClass)

fun AppCompatActivity.showAlertMessage(message: String) {
    val builder = AlertDialog.Builder(this)
    builder.setMessage(message)
    builder.setTitle("Alert!")

    var dialog = builder.create()
    dialog.show()
}

fun AppCompatActivity.getLocationFromAddress(strAddress: String): Observable<LatLng?> {
    var observable = Observable.create<LatLng?> { emitor ->
        val coder = Geocoder(this)
        val address: List<Address>?
        var result: LatLng? = null

        try {
            address = coder.getFromLocationName(strAddress, 5)
            if (address == null || address.size == 0) {
                emitor.onError(Throwable())
                return@create
            }

            val location = address[0]
            location.getLatitude()
            location.getLongitude()

            result = LatLng(location.getLatitude(), location.getLongitude())
            emitor.onNext(result)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    return observable
}

fun AppCompatActivity.setMapLocation(googleMap: GoogleMap?, location: LatLng) {
    if (googleMap == null) {
        return
    }

    googleMap.apply {
        clear()
        addMarker(MarkerOptions().position(location).title("Address!"))
        animateCamera(CameraUpdateFactory.newLatLngZoom(location, 13.0f))
    }
}