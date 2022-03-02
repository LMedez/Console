package com.luc.presentation.viewmodel

import android.app.Activity
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.ktx.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {

    private val appUpdateManager: AppUpdateManager by lazy { AppUpdateManagerFactory.create(application)}

    private var updateType = AppUpdateType.FLEXIBLE
    private var appUpdateInfo: AppUpdateInfo? = null

    private val _updateAvailable = MutableLiveData<Boolean>()
    val updateAvailable: LiveData<Boolean> = _updateAvailable

    private val _isDownloaded = MutableLiveData<Boolean>()
    val isDownloaded: LiveData<Boolean> = _isDownloaded

    private val _bytesDownloaded = MutableLiveData<Long>()
    val bytesDownloaded: LiveData<Long> = _bytesDownloaded

    private val _totalBytesToDownload  = MutableLiveData<Long>()
    val totalBytesToDownload: LiveData<Long> = _totalBytesToDownload

    val listener = InstallStateUpdatedListener { state ->
        if (state.installStatus() == InstallStatus.DOWNLOADING) {
            _bytesDownloaded.postValue(state.bytesDownloaded)
            _totalBytesToDownload.postValue(state.totalBytesToDownload)
        }
    }

    init {
        appUpdateManager.registerListener(listener)
        viewModelScope.launch {
            appUpdateManager.requestUpdateFlow().collect {
                when (it) {
                    is AppUpdateResult.Available -> {
                        _updateAvailable.postValue(true)
                        appUpdateInfo = appUpdateManager.requestAppUpdateInfo()

                        if (appUpdateInfo?.installStatus == InstallStatus.DOWNLOADED)
                            _isDownloaded.postValue(true)

                        appUpdateInfo?.let { appUpdate ->
                            if (appUpdate.updatePriority() < 3
                                && appUpdate.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)
                            ) {
                                updateType = AppUpdateType.FLEXIBLE

                            } else if (appUpdate.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                                updateType = AppUpdateType.IMMEDIATE
                            }
                        }
                    }

                    is AppUpdateResult.InProgress -> {}

                    is AppUpdateResult.Downloaded -> {
                        _isDownloaded.postValue(true)
                    }

                    is AppUpdateResult.NotAvailable -> {
                        _updateAvailable.postValue(false)
                    }
                }
            }
        }
    }

    fun startUpdate(activity: Activity) {
        appUpdateManager.startUpdateFlowForResult(
            // Pass the intent that is returned by 'getAppUpdateInfo()'.
            appUpdateInfo ?: return,
            // Or 'AppUpdateType.FLEXIBLE' for flexible updates.
            updateType,
            // The current activity making the update request.
            activity,
            // Include a request code to later monitor this update request.
            UPDATE_REQUEST_CODE
        )
    }

    fun completeUpdate() {
        appUpdateManager.completeUpdate()
        _isDownloaded.postValue(false)
    }

    override fun onCleared() {
        super.onCleared()
        appUpdateManager.unregisterListener(listener)

    }
}

const val UPDATE_REQUEST_CODE = 123