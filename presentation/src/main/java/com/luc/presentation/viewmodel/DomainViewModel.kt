package com.luc.presentation.viewmodel

import androidx.lifecycle.*
import com.luc.common.model.Caldera
import com.luc.common.model.Repuesto
import com.luc.common.model.Settings
import com.luc.domain.usecases.GetCalderaUseCase
import com.luc.domain.usecases.GetSettingsUseCase
import com.luc.presentation.utils.addPercent
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getCalderaUseCase: GetCalderaUseCase,
    private val getSettingsUseCase: GetSettingsUseCase
) : ViewModel() {

    private var _calderaList: List<Caldera>? = null
    private val currentRepuestoList: MutableList<Repuesto> = mutableListOf()

    private val _repuestoList = MutableLiveData<List<Repuesto>>()

    private val _settings = MutableLiveData<Settings>()
    val settings: LiveData<Settings> = _settings

    val calderaList: LiveData<List<Caldera>> = Transformations.switchMap(settings) {
        getCalderas(it.applyIva)
    }

    val repuestoList: MediatorLiveData<List<Repuesto>> = MediatorLiveData<List<Repuesto>>().apply {
        addSource(_settings) {

        }
    }

    private fun getCalderas(applyIva: Boolean) = liveData {
        if (_calderaList == null)
            _calderaList = getCalderaUseCase.getCalderas()

        if (applyIva) {
            _calderaList!!.map { caldera ->
                caldera.repuestos.map { repuesto ->
                    repuesto.precioService = addPercent(repuesto.precioService, 21)
                }
            }
        }
        emit(_calderaList)
    }

    fun mapRepuestoList(settings: Settings) {
        if (settings.applyGain) {
            currentRepuestoList.map {
                it.precioPublico
            }
        }
    }

    init {
        viewModelScope.launch {
            getSettingsUseCase.getSettings().collect { _settings.postValue(it) }
        }
    }

    fun removeRepuesto(repuesto: Repuesto) {
        currentRepuestoList.remove(repuesto)
        _repuestoList.postValue(currentRepuestoList)
    }

    fun addRepuesto(repuesto: Repuesto) {
        currentRepuestoList.add(repuesto)
        _repuestoList.postValue(currentRepuestoList)
    }
}