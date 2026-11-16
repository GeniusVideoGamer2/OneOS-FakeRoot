package com.oneos.fakeroot

import android.content.Context
import android.content.pm.PackageManager

/** A non-privileged Shizuku installation check.
 * This does not claim that the Shizuku service is running or that permission is granted.
 */
object ShizukuStatus {
    fun isInstalled(context: Context): Boolean = try {
        context.packageManager.getPackageInfo("moe.shizuku.privileged.api", 0)
        true
    } catch (_: PackageManager.NameNotFoundException) {
        false
    }
}
