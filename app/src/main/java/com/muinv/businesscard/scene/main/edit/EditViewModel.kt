package com.muinv.businesscard.scene.main.edit

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.muinv.businesscard.data.remote.data.Information
import com.muinv.businesscard.repository.InformationRepository
import com.muinv.businesscard.util.Event
import com.google.android.gms.maps.model.LatLng
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class EditViewModel(private val informationRepository: InformationRepository) : ViewModel() {
    var _information = MutableLiveData<Information>()
    val information: LiveData<Information>
        get() = _information

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

    @SuppressLint("CheckResult")
    fun editInfomation() {
        if (information.value == null) {
            _errorMessage.value = Event("Please pick information before")
            return
        }

        if (!validationData()) {
            return
        }

        var information = information.value
        informationRepository.update(information!!).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _errorMessage.value = Event("Create information success!")
            }, {
                _errorMessage.value = Event("Create information error!")
            })
    }

    @SuppressLint("CheckResult")
    fun deleteInfomation() {
        if (information.value == null) {
            _errorMessage.value = Event("Please pick information before")
            return
        }

        informationRepository.delete(information.value!!.id).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _errorMessage.value = Event("Delete information success!")
            }, {
                _errorMessage.value = Event("Delete information error!")
            })
    }

    fun validationData(): Boolean {
        if (_information.value?.name1 == "" || _information.value?.name2 == "" ||
            _information.value?.company == "" || _information.value?.department == "" ||
            _information.value?.postal == "" || _information.value?.address1 == "" ||
            _information.value?.address2 == ""
        ) {
            _errorMessage.value = Event("Please insert all value")
            return false
        }

        return true
    }
}
