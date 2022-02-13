package com.luc.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.luc.common.model.Caldera
import com.luc.domain.usecases.GetCalderaUseCase
import com.luc.domain.usecases.GetSettingsUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getCalderaUseCase: GetCalderaUseCase,
    private val getSettingsUseCase: GetSettingsUseCase
) : ViewModel() {

    private val _calderaList = MutableLiveData<List<Caldera>>()
    val calderaList: LiveData<List<Caldera>> = _calderaList

    init {
        viewModelScope.launch {
            val calderaList = getCalderaUseCase.getCalderas()

            getSettingsUseCase.getSettings().collect {
                if (it.applyIva) {
                    calderaList.map { caldera ->
                        caldera.repuestos.map { repuesto ->
                            val precioService = repuesto.precioService.toDouble()
                            val precioServiceIva = precioService + (precioService * 21) / 100
                            repuesto.precioService = String.format("%.2f", precioServiceIva)
                        }
                    }
                    _calderaList.postValue(calderaList)
                } else _calderaList.postValue(calderaList)
            }
        }
    }

    fun getCalderas() = liveData {
        emit(getCalderaUseCase.getCalderas())
    }
}