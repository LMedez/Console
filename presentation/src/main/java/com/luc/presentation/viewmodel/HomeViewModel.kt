package com.luc.presentation.viewmodel

import androidx.lifecycle.*
import com.luc.common.model.Caldera
import com.luc.common.model.Settings
import com.luc.domain.usecases.GetCalderaUseCase
import com.luc.domain.usecases.GetSettingsUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getCalderaUseCase: GetCalderaUseCase,
    private val getSettingsUseCase: GetSettingsUseCase
) : ViewModel() {

    private val _settings = MediatorLiveData<Settings>()
    val settings: LiveData<Settings> = _settings

    val calderaList: LiveData<List<Caldera>> = Transformations.switchMap(settings) {
        getCalderas(it.applyIva)
    }

    private fun getCalderas(applyIva: Boolean) = liveData {
        val listCalderas = getCalderaUseCase.getCalderas()

        if (applyIva) {
            listCalderas.map { caldera ->
                caldera.repuestos.map { repuesto ->
                    val precioService = repuesto.precioService.toDouble()
                    val precioServiceIva = precioService + (precioService * 21) / 100
                    repuesto.precioService = String.format("%.2f", precioServiceIva)
                }
            }
        }
        emit(listCalderas)
    }

    init {
        viewModelScope.launch {
            getSettingsUseCase.getSettings().collect { _settings.postValue(it) }
        }
    }
}