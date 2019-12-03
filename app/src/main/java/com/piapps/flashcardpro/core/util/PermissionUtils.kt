package com.piapps.flashcardpro.core.util

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings

/**
 * Created by abduaziz on 2019-09-22 at 00:44.
 */

object PermissionUtils {
    val READ_EXTERNAL_STORAGE = 234
    val WRITE_EXTERNAL_STORAGE = 18236
    val CAMERA = 235
    val LOCATION = 236
    val RECORD_AUDIO = 237
    var READ_CONTACTS = 238

    fun openPermissionSettings(activity: Activity) {
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        val uri = Uri.fromParts("package", activity.packageName, null)
        intent.data = uri
        activity.startActivity(intent)
    }

    fun permissionGranted(requestCode: Int, grantResults: IntArray, currentPermission: Int): Boolean {
        if (requestCode == currentPermission) {
            return (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
        }
        return false
    }
}