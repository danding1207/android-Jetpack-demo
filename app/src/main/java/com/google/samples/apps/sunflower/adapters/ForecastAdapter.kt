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

package com.google.samples.apps.sunflower.adapters

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.samples.apps.sunflower.PlantListFragment
import com.google.samples.apps.sunflower.databinding.ListItemForecastBinding
import com.google.samples.apps.sunflower.databinding.ListItemLifestyleBinding
import com.google.samples.apps.sunflower.http.bean.Forecast.HeWeather6Bean.DailyForecastBean

/**
 * Adapter for the [RecyclerView] in [PlantListFragment].
 */
class ForecastAdapter : ListAdapter<DailyForecastBean, ForecastAdapter.ViewHolder>(ForecastDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val lifestyleBean = getItem(position)
        holder.apply {
            bind(createOnClickListener(), lifestyleBean)
            itemView.tag = lifestyleBean
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ListItemForecastBinding.inflate(
                LayoutInflater.from(parent.context), parent, false))
    }

    private fun createOnClickListener(): View.OnClickListener {
        return View.OnClickListener {
//            val direction = PlantListFragmentDirections.ActionPlantListFragmentToPlantDetailFragment(plantId)
//            it.findNavController().navigate(direction)
        }
    }

    class ViewHolder(
        private val binding: ListItemForecastBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(listener: View.OnClickListener, item: DailyForecastBean) {
            binding.apply {
                clickListener = listener
                bean = item

                executePendingBindings()
            }
        }
    }
}