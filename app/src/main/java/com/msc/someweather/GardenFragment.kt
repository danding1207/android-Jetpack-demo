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

package com.msc.someweather

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.TextView
import androidx.navigation.findNavController
import com.msc.someweather.adapters.GardenPlantingAdapter
import com.msc.someweather.utilities.InjectorUtils
import com.msc.someweather.viewmodels.GardenPlantingListViewModel

class GardenFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_garden, container, false)
        val adapter = GardenPlantingAdapter(view.context)
        view.findViewById<RecyclerView>(R.id.garden_list).adapter = adapter
        subscribeUi(adapter)

        setHasOptionsMenu(true)
        return view
    }

    private fun subscribeUi(adapter: GardenPlantingAdapter) {
        val factory = InjectorUtils.provideGardenPlantingListViewModelFactory(requireContext())
        val viewModel = ViewModelProviders.of(this, factory)
                .get(GardenPlantingListViewModel::class.java)

        viewModel.gardenPlantings.observe(viewLifecycleOwner, Observer { plantings ->
            if (plantings != null && plantings.isNotEmpty()) {
                activity?.run {
                    findViewById<RecyclerView>(R.id.garden_list).run { visibility = View.VISIBLE }
                    findViewById<TextView>(R.id.empty_garden).run { visibility = View.GONE }
                }
            } else {
                activity?.run {
                    findViewById<RecyclerView>(R.id.garden_list).run { visibility = View.GONE }
                    findViewById<TextView>(R.id.empty_garden).run { visibility = View.VISIBLE }
                }
            }
        })

        viewModel.plantAndGardenPlantings.observe(viewLifecycleOwner, Observer { result ->
            if (result != null && result.isNotEmpty())
                adapter.submitList(result)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_weather, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.weather -> {
                val direction = GardenFragmentDirections.ActionGardenFragmentToWeatherFragment()
                view!!.findNavController().navigate(direction)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}
