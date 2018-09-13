
package com.msc.someweather.viewmodels

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.support.v4.app.Fragment

/**
 * Factory for creating a [TemperratureViewModel].
 */
class TemperratureViewModelFactory(private val fragment: Fragment) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TemperratureViewModel(fragment) as T
    }
}
