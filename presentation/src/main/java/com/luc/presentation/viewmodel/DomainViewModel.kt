package com.luc.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.luc.common.model.Caldera
import com.luc.common.model.Repuesto
import com.luc.common.model.Settings
import com.luc.domain.usecases.GetCalderaUseCase
import com.luc.domain.usecases.GetSettingsUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DomainViewModel(
    private val getCalderaUseCase: GetCalderaUseCase,
    private val getSettingsUseCase: GetSettingsUseCase
) : ViewModel() {

    private var _calderaList: List<Caldera>? = null

    private val _currentRepuestoList: MutableList<Repuesto> = mutableListOf()

    private val currentRepuestoList = MutableLiveData<List<Repuesto>>()

    private val _settings = MutableLiveData<Settings>()

    val calderaList: LiveData<List<Caldera>> = Transformations.switchMap(_settings) {
        getCalderas(it)
    }

    val repuestoList: MediatorLiveData<List<Repuesto>> =
        MediatorLiveData<List<Repuesto>>().apply {
            addSource(_settings) {
                applySettings(it)
            }
        }

    private fun getCalderas(settings: Settings) = liveData {
        if (_calderaList.isNullOrEmpty())
            _calderaList = getCalderaUseCase.getCalderas()

        _calderaList!!.map { caldera ->
            caldera.repuestos.map {
                it.settings = settings
            }
        }
        emit(_calderaList)
    }

    fun applySettings(settings: Settings) {
        _currentRepuestoList.map {
            it.settings = settings
        }
        currentRepuestoList.postValue(_currentRepuestoList)
    }

    init {
        viewModelScope.launch {
            getSettingsUseCase.getSettings().collect { _settings.postValue(it) }
        }

        repuestoList.addSource(currentRepuestoList) { list ->
            val listMapped = list.map { repuesto ->
                val calderaName = _calderaList?.find { it.id == repuesto.calderaId}?.caldera?:""
                repuesto.calderaName = calderaName
                repuesto
            }
            repuestoList.postValue(listMapped)
        }
    }

    fun removeRepuesto(repuesto: Repuesto) {
        _currentRepuestoList.remove(repuesto)
        currentRepuestoList.postValue(_currentRepuestoList)
    }

    fun addRepuesto(repuesto: Repuesto) {
        _currentRepuestoList.add(repuesto)
        currentRepuestoList.postValue(_currentRepuestoList)
    }
}

data class RepuestoConverted(
    val name: String,
    val servicePriceAndUsd: String,
    val publicoAndUsd: String,
    val servicePriceArs: String,
    val publicoPriceArs: String
)
