package com.example.businesscard.util

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import android.content.DialogInterface
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.appcompat.app.AlertDialog


fun <T : ViewModel> AppCompatActivity.obtainViewModel(viewModelClass: Class<T>) =
    ViewModelProviders.of(this, ViewModelFactory.getInstance(application)).get(viewModelClass)

fun AppCompatActivity.showAlertMessage(message: String) {
    val builder = AlertDialog.Builder(this)
    builder.setMessage(message)
    builder.setTitle("Alert!")

    var dialog = builder.create()
    dialog.show()
}