package com.luc.presentation.di

import com.luc.presentation.viewmodel.DomainViewModel
import com.luc.presentation.viewmodel.MainActivityViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel { DomainViewModel(get(), get(), get()) }
    viewModel { MainActivityViewModel(get()) }
}