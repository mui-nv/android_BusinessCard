package com.example.businesscard.scene.main.create

import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.businesscard.data.remote.data.Information
import com.example.businesscard.repository.InformationRepository
import com.example.businesscard.util.Event
import com.example.businesscard.util.getLocationFromAddress
import com.google.android.gms.maps.model.LatLng
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CreateViewModel(private val informationRepository: InformationRepository) : ViewModel() {
    var name1 = MutableLiveData<String>()
    var name2 = MutableLiveData<String>()
    var company = MutableLiveData<String>()
    var department = MutableLiveData<String>()
    var postal = MutableLiveData<String>()
    var address1 = MutableLiveData<String>()
    var address2 = MutableLiveData<String>()
    var _location = MutableLiveData<LatLng>()
    val location: LiveData<LatLng>
        get() = _location

    var _errorMessage = MutableLiveData<Event<String>>()
    val errorMessage: LiveData<Event<String>>
        get() = _errorMessage

    fun saveInfomation() {
        if (!validationData()) {
            return
        }

        var information = Information(0, 0, name1.value, name2.value, company.value,
            department.value, postal.value, address1.value, address2.value,
            location.value?.latitude, location.value?.longitude, "ns2")
        informationRepository.create(information).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _errorMessage.value = Event("Create information success!")
            }, {
                _errorMessage.value = Event("Create information error!")
            })
    }

    fun validationData(): Boolean {
        if (name1.value == "" || name2.value == "" || company.value == "" || department.value == "" || postal.value == ""
            || address2.value == "" || address1.value == "" || location.value == null) {

            _errorMessage.value = Event("Please insert all value")
            return false
        }

        return true
    }
}
