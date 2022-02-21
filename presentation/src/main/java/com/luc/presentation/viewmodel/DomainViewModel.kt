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

    private val _settings = MutableLiveData<Settings>()
    val settings: LiveData<Settings> = _settings

    val _repuestoList = MutableLiveData<List<Repuesto>>()
    private var _calderaList = listOf<Caldera>()

    private val _currentRepuestoList: MutableList<Repuesto> = mutableListOf()

    private val _selectedRepuestoList = MutableLiveData<List<Repuesto>>()
    val selectedRepuestoList: LiveData<List<Repuesto>> =
        Transformations.map(_selectedRepuestoList) { list ->
            list.map { repuesto ->
                repuesto.calderaName =
                    _calderaList.find { it.id == repuesto.calderaId }?.caldera ?: ""
            }
            list
        }

    fun repuestoList(calderaId: String): LiveData<List<Repuesto>> =
        Transformations.map(_repuestoList) { list ->
            list.filter { it.calderaId == calderaId }
        }

    val getCalderas = liveData {
        _calderaList = getCalderaUseCase.getCalderas()
        emit(_calderaList)
    }

    init {
        viewModelScope.launch {
            _repuestoList.postValue(getCalderaUseCase.getRepuestos())
            getSettingsUseCase.getSettings().collect { settings ->
                _settings.postValue(settings)
                _repuestoList.postValue(_repuestoList.value?.map {
                    it.settings = settings
                    it
                })
                _selectedRepuestoList.postValue(_selectedRepuestoList.value?.map {
                    it.settings = settings; it
                }?: listOf())
            }
        }
    }

    fun removeRepuesto(repuesto: Repuesto) {
        _currentRepuestoList.remove(repuesto)
        _selectedRepuestoList.postValue(_currentRepuestoList)
    }

    fun addRepuesto(repuesto: Repuesto) {
        _currentRepuestoList.add(repuesto)
        _selectedRepuestoList.postValue(_currentRepuestoList)
    }

    fun updateSettings(settings: Settings) {
        viewModelScope.launch {
            getSettingsUseCase.updateSettings(settings)
        }
    }

}