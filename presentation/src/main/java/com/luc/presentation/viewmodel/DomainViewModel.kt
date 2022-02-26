package com.luc.presentation.viewmodel

import androidx.lifecycle.*
import com.luc.common.NetworkStatus
import com.luc.common.model.Caldera
import com.luc.common.model.Repuesto
import com.luc.common.model.Settings
import com.luc.domain.usecases.GetCalderaUseCase
import com.luc.domain.usecases.GetSettingsUseCase
import com.luc.domain.usecases.SendEmailUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DomainViewModel(
    private val getCalderaUseCase: GetCalderaUseCase,
    private val getSettingsUseCase: GetSettingsUseCase,
    private val sendEmailUseCase: SendEmailUseCase
) : ViewModel() {

    private val _settings = MutableLiveData<Settings>()
    val settings: LiveData<Settings> = _settings

    private val _email = MutableLiveData<NetworkStatus<String>>()
    val email: LiveData<NetworkStatus<String>> = _email

    private val _repuestoList = MutableLiveData<List<Repuesto>>()
    private var _calderaList = listOf<Caldera>()

    private val _currentRepuestoList: MutableList<Repuesto> = mutableListOf()

    private val _selectedRepuestoList = MutableLiveData<List<Repuesto>>()
    val selectedRepuestoList: LiveData<List<Repuesto>> =
        Transformations.map(_selectedRepuestoList) { list ->
            val calderaNameMapped = list.map { repuesto ->
                repuesto.calderaName =
                    _calderaList.find { it.id == repuesto.calderaId }?.caldera ?: ""
                repuesto
            }

            calderaNameMapped.groupBy { it.id }.filter { it.value.size > 1 }.forEach { map ->
                calderaNameMapped.map {
                    if (it.id == map.key) {
                        it.count = map.value.size
                    }
                }
            }
            calderaNameMapped.distinct()

        }

    fun repuestoList(calderaId: String): LiveData<List<Repuesto>> =
        Transformations.map(_repuestoList) { list ->
            list.filter { it.calderaId == calderaId }
        }

    val getCalderas = liveData {
        _calderaList = getCalderaUseCase.getCalderas()
        emit(_calderaList)
    }

    fun sendEmail() = viewModelScope.launch(Dispatchers.IO) {
        _email.postValue(NetworkStatus.Loading)
        val result = sendEmailUseCase.sendEmail(_currentRepuestoList)
        _email.postValue(result)
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
                } ?: listOf())
            }
        }
    }

    fun clearSelectedRepuestoList() {
        _currentRepuestoList.clear()
        _selectedRepuestoList.postValue(_currentRepuestoList)
    }

    fun removeRepuesto(repuesto: Repuesto) {
        _currentRepuestoList.remove(repuesto)
        _selectedRepuestoList.postValue(_currentRepuestoList)
    }

    fun addRepuesto(repuesto: Repuesto) {
        repuesto.count = 1
        _currentRepuestoList.add(repuesto)
        _selectedRepuestoList.postValue(_currentRepuestoList)
    }

    fun updateSettings(settings: Settings) {
        viewModelScope.launch {
            getSettingsUseCase.updateSettings(settings)
        }
    }
}