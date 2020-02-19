package com.example.businesscard.scene.main.search

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.businesscard.data.remote.data.Information
import com.example.businesscard.repository.InformationRepository
import com.example.businesscard.util.Event
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SearchViewModel(val informationRepository: InformationRepository) : ViewModel() {
    var _items = MutableLiveData<List<Information>>().apply { value = emptyList() }
    val items: LiveData<List<Information>>
        get() = _items

    var _errorMessage = MutableLiveData<Event<String>>()
    val errorMessage: LiveData<Event<String>>
        get() = _errorMessage

    private val _openInformationEvent = MutableLiveData<Event<Information>>()
    val openInformationEvent: LiveData<Event<Information>>
        get() = _openInformationEvent

    @SuppressLint("CheckResult")
    fun getDatas() {
        informationRepository.allData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _items.value = it
            }, {
                _errorMessage.value = Event("Get data fail!")
            })
    }

    @SuppressLint("CheckResult")
    fun deleteInformation(information: Information) {
        informationRepository.delete(information.id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                val listItem = items.value?.toMutableList()
                listItem?.remove(information)
                _items.value = listItem
            }, {
                _errorMessage.value = Event("Delete user fail!")
            })
    }

    fun selectedInformation(information: Information) {
        _openInformationEvent.value = Event(information)
    }
}
