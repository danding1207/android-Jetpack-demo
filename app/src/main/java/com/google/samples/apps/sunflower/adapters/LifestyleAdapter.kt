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
import com.google.samples.apps.sunflower.databinding.ListItemLifestyleBinding
import com.google.samples.apps.sunflower.http.bean.Lifestyle.HeWeather6Bean.LifestyleBean

/**
 * Adapter for the [RecyclerView] in [PlantListFragment].
 */
class LifestyleAdapter : ListAdapter<LifestyleBean, LifestyleAdapter.ViewHolder>(LifestyleDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val lifestyleBean = getItem(position)
        holder.apply {
            bind(createOnClickListener(), lifestyleBean)
            itemView.tag = lifestyleBean
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ListItemLifestyleBinding.inflate(
                LayoutInflater.from(parent.context), parent, false))
    }

    private fun createOnClickListener(): View.OnClickListener {
        return View.OnClickListener {
//            val direction = PlantListFragmentDirections.ActionPlantListFragmentToPlantDetailFragment(plantId)
//            it.findNavController().navigate(direction)
        }
    }

    class ViewHolder(
        private val binding: ListItemLifestyleBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(listener: View.OnClickListener, item: LifestyleBean) {
            binding.apply {
                clickListener = listener
                bean = item

                when(item.type) {
                    "comf"-> binding.tvType.text = "舒适度指数"
                    "cw"-> binding.tvType.text = "洗车指数"
                    "drsg"-> binding.tvType.text = "穿衣指数"
                    "flu"-> binding.tvType.text = "感冒指数"
                    "sport"-> binding.tvType.text = "运动指数"
                    "trav"-> binding.tvType.text = "旅游指数"
                    "uv"-> binding.tvType.text = "紫外线指数"
                    "air"-> binding.tvType.text = "空气污染扩散条件指数"
                    "ac"-> binding.tvType.text = "空调开启指数"
                    "ag"-> binding.tvType.text = "过敏指数"
                    "gl"-> binding.tvType.text = "太阳镜指数"
                    "mu"-> binding.tvType.text = "化妆指数"
                    "airc"-> binding.tvType.text = "晾晒指数"
                    "ptfc"-> binding.tvType.text = "交通指数"
                    "fsh"-> binding.tvType.text = "钓鱼指数"
                    "spi"-> binding.tvType.text = "防晒指数"
                }

                executePendingBindings()
            }
        }
    }
}