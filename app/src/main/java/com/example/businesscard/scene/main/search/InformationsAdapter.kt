/*
 *  Copyright 2017 Google Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.example.businesscard.scene.main.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.databinding.DataBindingUtil
import com.example.businesscard.data.remote.data.Information
import com.example.businesscard.databinding.InformationItemBinding

class InformationsAdapter(
    private var informations: List<Information>,
    private val tasksViewModel: SearchViewModel
) : BaseAdapter() {

    fun replaceData(informations: List<Information>) {
        setList(informations)
    }

    override fun getCount() = informations.size

    override fun getItem(position: Int) = informations[position]

    override fun getItemId(position: Int) = position.toLong()

    override fun getView(position: Int, view: View?, viewGroup: ViewGroup): View {
        val binding: InformationItemBinding
        binding = if (view == null) {
            // Inflate
            val inflater = LayoutInflater.from(viewGroup.context)

            // Create the binding
            InformationItemBinding.inflate(inflater, viewGroup, false)
        } else {
            // Recycling view
            DataBindingUtil.getBinding(view) ?: throw IllegalStateException()
        }

        with(binding) {
            information = informations[position]
        }

        return binding.root
    }


    private fun setList(infomations: List<Information>) {
        this.informations = infomations
        notifyDataSetChanged()
    }
}
