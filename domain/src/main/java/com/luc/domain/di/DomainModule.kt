package com.luc.domain.di

import com.luc.domain.usecases.GetCalderaUseCase
import com.luc.domain.usecases.GetSettingsUseCase
import org.koin.dsl.module

val domainModule = module {
    factory  { GetCalderaUseCase(domainRepository = get()) }
    factory  { GetSettingsUseCase(domainRepository = get()) }
}