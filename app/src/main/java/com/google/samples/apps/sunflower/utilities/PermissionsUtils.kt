package com.google.samples.apps.sunflower.utilities

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.support.v4.app.ActivityCompat

object PermissionsUtils {

    private val isMarshmallow: Boolean
        get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M

    fun isGranted(context: Context, permission: String): LiveData<Boolean> {
        val isGranted = MutableLiveData<Boolean>()
        isGranted.value = !isMarshmallow || isGranted_(context, permission)
        return isGranted
    }

    private fun isGranted_(context: Context, permission: String): Boolean {
        val checkSelfPermission = ActivityCompat.checkSelfPermission(context, permission)
        return checkSelfPermission == PackageManager.PERMISSION_GRANTED
    }

}
