package com.vodafone.weatherapp.navigation

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import com.vodafone.core.utils.Constants.CITY_NAME_KEY

internal data class Actions(val context: Context) {
    fun onDynamicClick(cityName: String) {
        installForecastModule(
            context = context,
            onSuccess = {
                val intent = Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse("app://com.forecast.dynamic")
                    `package` = context.packageName
                    putExtra(CITY_NAME_KEY, cityName)
                }
                context.startActivity(intent)
            },
            onFailure = { e ->
                Log.e("Dynamic Actions", "${e.message}")
            }
        )
    }
}

fun installForecastModule(
    context: Context,
    onSuccess: () -> Unit,
    onFailure: (Exception) -> Unit
) {
    val splitInstallManager: SplitInstallManager = SplitInstallManagerFactory.create(context)
    val moduleName = "Forecast"
    if (splitInstallManager.installedModules.contains(moduleName)) {
        onSuccess()
        return
    }
    try {
        val request = SplitInstallRequest.newBuilder()
            .addModule(moduleName)
            .build()

        val installTask = splitInstallManager.startInstall(request)

        installTask.addOnSuccessListener {
            onSuccess()
        }.addOnFailureListener { exception ->
            Log.e("Forecast Module Installation", "${exception.message}")
            onFailure(exception)
        }
    } catch (e: Exception) {
        Log.e("Forecast Module Installation", "${e.message}")
        onFailure(e)
    }
}

