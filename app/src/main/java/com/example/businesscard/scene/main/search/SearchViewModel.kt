package com.example.businesscard.scene.main.search

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.businesscard.data.remote.data.Information
import com.example.businesscard.repository.InformationRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SearchViewModel(val informationRepository: InformationRepository) : ViewModel() {
    var _items = MutableLiveData<List<Information>>().apply { value = emptyList() }
    val items: LiveData<List<Information>>
        get() = _items

    var errorMessage = MutableLiveData<String>()
    val _errorMessage: LiveData<String>
        get() = errorMessage

    @SuppressLint("CheckResult")
    fun getDatas() {
        informationRepository.allData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _items.value = it
            }, {
                errorMessage.postValue(it.message)
            })
    }
}
