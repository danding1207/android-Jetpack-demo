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

import android.support.v7.util.DiffUtil
import com.google.samples.apps.sunflower.http.bean.Lifestyle.HeWeather6Bean.LifestyleBean

class LifestyleDiffCallback : DiffUtil.ItemCallback<LifestyleBean>() {

    override fun areItemsTheSame(oldItem: LifestyleBean, newItem: LifestyleBean): Boolean {
        return oldItem.brf == newItem.brf
                && oldItem.txt == newItem.txt
                && oldItem.type == newItem.type
    }

    override fun areContentsTheSame(oldItem: LifestyleBean, newItem: LifestyleBean): Boolean {
        return oldItem == newItem
    }

}