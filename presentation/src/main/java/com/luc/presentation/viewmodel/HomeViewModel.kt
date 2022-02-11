package com.luc.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.luc.domain.usecases.GetCalderaUseCase

class HomeViewModel(private val getCalderaUseCase: GetCalderaUseCase) : ViewModel() {

    fun getCalderas() = liveData {
        emit(getCalderaUseCase.getCalderas())
    }
}