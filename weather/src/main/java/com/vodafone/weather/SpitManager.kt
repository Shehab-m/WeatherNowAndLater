package com.vodafone.weather

import android.content.Context
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory

fun isModuleInstalled(context: Context): Boolean {
    val splitInstallManager: SplitInstallManager = SplitInstallManagerFactory.create(context)
    val moduleName = "Forecast"
    return splitInstallManager.installedModules.contains(moduleName)
}