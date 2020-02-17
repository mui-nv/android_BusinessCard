package com.example.businesscard.scene.main.search

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.businesscard.data.remote.data.Information
import com.example.businesscard.repository.InformationRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SearchViewModel(val informationRepository: InformationRepository) : ViewModel() {
    var datas = MutableLiveData<List<Information>>()
    val _datas: LiveData<List<Information>>
        get() = datas

    var errorMessage = MutableLiveData<String>()
    val _errorMessage: LiveData<String>
        get() = errorMessage

    @SuppressLint("CheckResult")
    fun getDatas() {
        informationRepository.allData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                datas.postValue(it)
                Log.i("TAG1", it.size.toString())
            }, {
                errorMessage.postValue(it.message)
            })
    }
}
