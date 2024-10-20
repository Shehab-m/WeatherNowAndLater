package com.vodafone.weatherapp.navigation

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.google.android.play.core.splitinstall.SplitInstallException
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import com.google.android.play.core.splitinstall.SplitInstallSessionState
import com.google.android.play.core.splitinstall.SplitInstallStateUpdatedListener
import com.google.android.play.core.splitinstall.model.SplitInstallErrorCode
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus
import com.vodafone.core.utils.Constants.CITY_NAME_KEY

const val TAG = "dynamic_module_util"
const val FORECAST_MODULE = "Forecast"

internal class DynamicModuleDownloadUtil(
    private val context: Context,
) {
    private var logState = mutableStateOf("Activity Log:\n")
    private lateinit var splitInstallManager: SplitInstallManager
    private var mySessionId = 0

    init {
        if (!::splitInstallManager.isInitialized) {
            splitInstallManager = SplitInstallManagerFactory.create(context)
        }
    }

    private fun isModuleDownloaded(moduleName: String): Boolean {
        return splitInstallManager.installedModules.contains(moduleName)
    }

    private fun installForecastModule(
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        if (isModuleDownloaded(FORECAST_MODULE)) {
            onSuccess()
        } else {
            val request = SplitInstallRequest.newBuilder()
                .addModule(FORECAST_MODULE)
                .build()
            val listener = SplitInstallStateUpdatedListener { state -> handleInstallStates(state) }
            splitInstallManager.registerListener(listener)

            splitInstallManager.startInstall(request)
                .addOnSuccessListener { sessionId ->
                    mySessionId = sessionId
                    onSuccess()
                }
                .addOnFailureListener { e ->
                    Log.d(TAG, "Exception: ${e.message}")
                    handleInstallFailure((e as SplitInstallException).errorCode)
                    onFailure(e)
                }

            splitInstallManager.unregisterListener(listener)
        }
    }

    private fun handleInstallFailure(errorCode: Int) {
        when (errorCode) {
            SplitInstallErrorCode.NETWORK_ERROR -> {
                Log.e(TAG,"No internet found")
            }

            SplitInstallErrorCode.MODULE_UNAVAILABLE -> {
                Log.e(TAG,"Module unavailable")
            }

            SplitInstallErrorCode.ACTIVE_SESSIONS_LIMIT_EXCEEDED -> {
                Log.e(TAG,"Active session limit exceeded")
            }

            SplitInstallErrorCode.INSUFFICIENT_STORAGE -> {
                Log.e(TAG,"Insufficient storage")
            }

            SplitInstallErrorCode.PLAY_STORE_NOT_FOUND -> {
                Log.e(TAG,"Google Play Store Not Found!")
            }

            else -> {
                Log.e(TAG,"Something went wrong! Try again later")
            }
        }
    }

    @SuppressLint("SwitchIntDef")
    private fun handleInstallStates(state: SplitInstallSessionState) {
        if (state.sessionId() == mySessionId) {
            when (state.status()) {
                SplitInstallSessionStatus.DOWNLOADING -> {
                    Log.e(TAG,"Something went wrong! Try again later")
                    logState.value += "${getCurrentTimestamp()}: Downloading...\n"
                    Log.d(TAG, "handleInstallStates: ${logState.value}")
                }

                SplitInstallSessionStatus.DOWNLOADED -> {
                    logState.value += "${getCurrentTimestamp()}: Module download completed.\n"
                    Log.d(TAG, "handleInstallStates: ${logState.value}")
                }

                SplitInstallSessionStatus.INSTALLED -> {
                    Log.d(TAG, "Dynamic Module downloaded")
                    logState.value += "${getCurrentTimestamp()}: Module install Success!\n"
                    Log.d(TAG, "handleInstallStates: ${logState.value}")
                }

                SplitInstallSessionStatus.FAILED -> {
                    logState.value += "${getCurrentTimestamp()}: Module download or installation failed.\n"
                    Log.d(TAG, "handleInstallStates: ${logState.value}")
                }

                SplitInstallSessionStatus.CANCELED -> {
                    logState.value += "${getCurrentTimestamp()}: Module download or installation canceled.\n"
                    Log.d(TAG, "handleInstallStates: ${logState.value}")
                }
            }
        }
    }

    fun onDynamicClick(cityName: String) {
        installForecastModule(
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

private fun getCurrentTimestamp(): Long {
    return System.currentTimeMillis()
}