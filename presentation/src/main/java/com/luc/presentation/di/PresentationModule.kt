package com.luc.presentation.di

import com.luc.presentation.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel { HomeViewModel(get()) }
}