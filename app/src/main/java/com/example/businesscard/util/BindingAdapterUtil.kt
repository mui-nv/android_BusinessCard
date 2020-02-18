package com.example.businesscard.util

import android.graphics.BitmapFactory
import android.util.Base64
import android.widget.ImageView
import android.widget.ListView
import androidx.databinding.BindingAdapter
import com.example.businesscard.data.remote.data.Information
import com.example.businesscard.scene.main.search.InformationsAdapter


object BindingAdapterUtil {
    @BindingAdapter("app:items")
    @JvmStatic
    fun setItems(listView: ListView, items: List<Information>) {
        with(listView.adapter as InformationsAdapter) {
            replaceData(items)
        }
    }

    @BindingAdapter("app:imageString")
    @JvmStatic
    fun setImage(image: ImageView, string: String) {
        var imageBytes = Base64.decode(string, Base64.DEFAULT)
        val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
        image.setImageBitmap(decodedImage)
    }
}