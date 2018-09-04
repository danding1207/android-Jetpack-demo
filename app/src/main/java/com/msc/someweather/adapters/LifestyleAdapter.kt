/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.msc.someweather.adapters

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.msc.someweather.PlantListFragment
import com.msc.someweather.databinding.ListItemLifestyleBinding
import com.msc.someweather.http.bean.Lifestyle

/**
 * Adapter for the [RecyclerView] in [WeatherFragment].
 */
class LifestyleAdapter : ListAdapter<Lifestyle, LifestyleAdapter.ViewHolder>(LifestyleDiffCallback()) {

    var simpleList: List<Lifestyle>? = null
    var compList: List<Lifestyle>? = null
    var isCompList: Boolean = false

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val lifestyleBean = getItem(position)
        holder.apply {
            bind(createOnClickListener(position), lifestyleBean)
            itemView.tag = lifestyleBean
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ListItemLifestyleBinding.inflate(
                LayoutInflater.from(parent.context), parent, false))
    }

    private fun createOnClickListener(position: Int): View.OnClickListener {
        return View.OnClickListener {
            if (position == 0) {
                toggle()
            }
        }
    }

    class ViewHolder(
            private val binding: ListItemLifestyleBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(listener: View.OnClickListener, item: Lifestyle) {
            binding.apply {
                clickListener = listener
                bean = item
                executePendingBindings()
            }
        }
    }


    private fun toggle() {
        if (isCompList) {
            submitList(simpleList)
        } else {
            submitList(compList)
        }
        isCompList = !isCompList
    }


}